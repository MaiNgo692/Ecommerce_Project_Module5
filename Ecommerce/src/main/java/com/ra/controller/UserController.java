package com.ra.controller;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.ChangePasswordRequest;
import com.ra.model.dto.request.UserRoleRequest;
import com.ra.model.dto.request.UserUpdateRequest;
import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.UsersEntity;
import com.ra.repository.UserRoleRepository;
import com.ra.service.FileService;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private CommonFunction<UserResponse> commonFunction;

    @GetMapping("/user/account")
    @ResponseBody
    public Map<String, Object> getAccount(@RequestParam("userId") long userId) {
        UserResponse response = userService.findByUserId(userId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        if (response != null) {
            rtn.put("data", response);
        } else {
            rtn.put("message", new BaseException("RA-00-03").getMessage());
        }
        return rtn;
    }

    @RequestMapping(value = "/user/account", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    @ResponseBody
    public Map<String, Object> updateAccount(@RequestParam("fullName") String fullName, @RequestParam("email") String email,
                                             @RequestParam("phone") String phone, @RequestParam("address") String address,
                                             @RequestParam("file") MultipartFile file, @RequestParam("userId") long userId) {
        fileService.save(file);
        Resource resource = fileService.load(file.getOriginalFilename());
        String imageUrl = "";
        try {
            imageUrl = String.valueOf((resource.getURL()));
        } catch (IOException exception) {
        }
        UserUpdateRequest request = new UserUpdateRequest();
        request.setFullName(fullName);
        request.setEmail(email);
        request.setAddress(address);
        request.setPhone(phone);
        request.setFile(imageUrl);
        UserResponse response = userService.update(request, userId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        if (response != null) {
            rtn.put("data", response);
        } else {
            rtn.put("message", new BaseException("RA-00-03").getMessage());
        }
        return rtn;
    }

    @RequestMapping(value = "/user/account/change-password", method = RequestMethod.PUT)
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest request, @RequestParam("userId") long userId) {
        return userService.changePassword(request,userId);
    }

    @GetMapping("/admin/users")
    @ResponseBody
    public Map<String, Object> getUsers(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                        @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
                                        @RequestParam(name = "sort_by") String sortField,
                                        @RequestParam(name = "how_sort") String sortType) {
        Page<UserResponse> response = userService.findAllPageAndSort(sortField, sortType, pageNumber, pageSize);
        return commonFunction.mapResponse(response, pageNumber, pageSize, sortField, sortType);
    }

    @PostMapping("/admin/users/{userId}/role/{roleId}")
    @ResponseBody
    public Map<String, Object> addRole(@PathVariable("userId") long userId, @PathVariable("roleId") long roleId) {
        UserRoleRequest response = userRoleRepository.findUserRoleRequestByRoleIdAndAndUserId(roleId, userId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        if (response != null) {
            rtn.put("message", "User already has permissions !");
        } else {
            UserRoleRequest roleRequest = new UserRoleRequest(userId, roleId);
            userRoleRepository.save(roleRequest);
            rtn.put("message", "Add permission success !");
        }
        return rtn;
    }

    @DeleteMapping("/admin/users/{userId}/role/{roleId}")
    @ResponseBody
    public Map<String, Object> deleteRole(@PathVariable("userId") long userId, @PathVariable("roleId") long roleId) {
        UserRoleRequest response = userRoleRepository.findUserRoleRequestByRoleIdAndAndUserId(roleId, userId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        if (response == null) {
            rtn.put("message", "The user does not have this permission !");
        } else {
            userRoleRepository.delete(response);
            rtn.put("message", "Remove permission success !");
        }
        return rtn;
    }

    @RequestMapping(value = "/admin/users/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> activeInActiveUser(@PathVariable("userId") long userId) {
        UsersEntity response = userService.activeInActiveUser(userId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("message", response.getStatus() ? "UnBlock user success !" : "Block user success !");
        return rtn;
    }

    @GetMapping("/admin/users/search")
    @ResponseBody
    public Map<String, Object> getUsersByName(@RequestParam(name = "searchName") String searchName,
                                              @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                              @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
                                              @RequestParam(name = "sort_by") String sortField,
                                              @RequestParam(name = "how_sort") String sortType) {
        Page<UserResponse> response = userService.findByNamePageAndSort(searchName, sortField, sortType, pageNumber, pageSize);
        return commonFunction.mapResponse(response, pageNumber, pageSize, sortField, sortType);
    }
}
