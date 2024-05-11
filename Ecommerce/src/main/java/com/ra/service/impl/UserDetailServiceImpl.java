package com.ra.service.impl;

import com.ra.advice.CommonHandlerException;
import com.ra.exception.BaseException;
import com.ra.model.dto.request.*;
import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.RoleEntity;
import com.ra.model.entity.UsersEntity;
import com.ra.repository.RoleRepository;
import com.ra.repository.UserRepository;
import com.ra.repository.UserRoleRepository;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CommonHandlerException commonHandlerException;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usersEntity = userRepository.findUsersEntityByUsername(username);
        if(usersEntity!=null){
            UserDetails userDetails = new UserDetailsAdapter(usersEntity);
            return userDetails;
        }
        throw new UsernameNotFoundException("Username \"" + username + "\" not found!");
    }
    public UsersEntity findByUsername(String username) {
        UsersEntity usersEntity = userRepository.findUsersEntityByUsername(username);
        if(usersEntity!=null){
            return usersEntity;
        }
        throw new UsernameNotFoundException("Username \"" + username + "\" not found!");
    }
    @Override
    public ResponseEntity add(UserRequest request) {

        UsersEntity usersEntity = new UsersEntity();
        try{
            usersEntity.setUsername(request.getUsername());
            usersEntity.setEmail(request.getEmail());
            usersEntity.setFullname(request.getFullName());
            usersEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            usersEntity.setPhone(request.getPhone());
            usersEntity.setAddress(request.getAddress());
            usersEntity.setCreatedAt(new Date());
            usersEntity.setUpdateAt(new Date());
            usersEntity.setStatus(true);
            userRepository.save(usersEntity);
            List<UserRoleRequest> roleEntities=new ArrayList<>();
            List<String> roles = request.getRoles();
            roles.forEach(role->{
                RoleEntity roleEntity = roleRepository.findByRoleName("ROLE_"+role);
                UserRoleRequest userRoleEntity = new UserRoleRequest(usersEntity.getUserId(), roleEntity.getRoleId());
                userRoleRepository.save(userRoleEntity);
                roleEntities.add(userRoleEntity);
            });
        }catch (Exception exception){
           throw exception;
        }
        return ResponseEntity.ok(new BaseException("RA-01-01").getErrorMessage()) ;
    }
    @Override
    public UserResponse findByUserId(long userId){
        UsersEntity usersEntity = userRepository.findById(userId).get();
        return convertToUseResponse(usersEntity);
    }
    @Override
    public UsersEntity activeInActiveUser(long userId){
        UsersEntity usersEntity = userRepository.findById(userId).get();
        usersEntity.setStatus(!usersEntity.getStatus());
        userRepository.save(usersEntity);
        return usersEntity;
    }
    @Override
    public UserResponse update(UserUpdateRequest request, long userId){
        UsersEntity usersEntity = userRepository.findById(userId).get();
        usersEntity.setFullname(request.getFullName());
        usersEntity.setEmail(request.getEmail());
        usersEntity.setPhone(request.getPhone());
        usersEntity.setAddress(request.getAddress());
        usersEntity.setAvatar(request.getFile());
        userRepository.save(usersEntity);
        return convertToUseResponse(usersEntity);
    }

    @Override
    public ResponseEntity changePassword(ChangePasswordRequest request, long userId){
        UsersEntity usersEntity = userRepository.findById(userId).get();
            if(passwordEncoder.matches(request.getOldPassword(),usersEntity.getPassword())){
                if(request.getNewPassword().equals(request.getConfirmPassword())){
                    usersEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
                    userRepository.save(usersEntity);
                    return new ResponseEntity<>(new BaseException("RA-01-03").getErrorMessage(), HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(new BaseException("RA-00-07").getErrorMessage(), HttpStatus.BAD_REQUEST);
                }
            }else {
                return new ResponseEntity<>(new BaseException("RA-00-08").getErrorMessage(), HttpStatus.BAD_REQUEST);
            }
    }
    @Override
    public Page<UserResponse> findAllPageAndSort(String field, String direction, int page, int pageSize){
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        Pageable pageable = (page == 1)? PageRequest.of(page-1, pageSize, sort)
                :PageRequest.of(page-1, pageSize);
        Page<UsersEntity> usersEntityPage = userRepository.findAllQuery(pageable);
        return usersEntityPage.map(this::convertToUseResponse);
    }
    @Override
    public Page<UserResponse> findByNamePageAndSort(String searchName,String field, String direction, int page, int pageSize){
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        Pageable pageable = (page == 1)? PageRequest.of(page-1, pageSize, sort)
                :PageRequest.of(page-1, pageSize);
        Page<UsersEntity> usersEntityPage = userRepository.findUsersEntitiesByFullnameContainingIgnoreCase(searchName,pageable);
        return usersEntityPage.map(this::convertToUseResponse);
    }
    private UserResponse convertToUseResponse(UsersEntity usersEntity){
        UserResponse response = new UserResponse();
        response.setUserName(usersEntity.getUsername());
        response.setEmail(usersEntity.getEmail());
        response.setFullName(usersEntity.getFullname());
        response.setAvatarUrl(usersEntity.getAvatar());
        response.setPhone(usersEntity.getPhone());
        response.setAddress(usersEntity.getAddress());
        return response;
    }


}
