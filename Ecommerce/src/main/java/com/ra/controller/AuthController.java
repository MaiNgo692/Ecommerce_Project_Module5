package com.ra.controller;

import com.ra.advice.CommonHandlerException;
import com.ra.model.dto.request.TokenRequest;
import com.ra.model.dto.request.UserDetailsAdapter;
import com.ra.model.dto.request.UserRequest;
import com.ra.model.dto.response.TokenResponse;
import com.ra.model.entity.RoleEntity;
import com.ra.model.entity.UsersEntity;
import com.ra.repository.RoleRepository;
import com.ra.service.RoleService;
import com.ra.service.impl.UserDetailServiceImpl;
import com.ra.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/auth")
public class AuthController {
    @Autowired
    private CommonHandlerException commonHandlerException;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody UserRequest request){
        return userDetailService.add(request);
    }
    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody TokenRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsAdapter userDetails = (UserDetailsAdapter)authentication.getPrincipal();
            UsersEntity usersEntity = userDetailService.findByUsername(userDetails.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setId(usersEntity.getUserId());
            tokenResponse.setFullName(usersEntity.getFullname());
            tokenResponse.setAccessToken(token);
            tokenResponse.setRefreshToken(token);
            List<String> roles = new ArrayList<>();
            usersEntity.getUserRoleEntities().forEach(role->{
                        RoleEntity roleEntity = roleRepository.findById(role.getRoleId()).get();
                        roles.add(roleEntity.getRoleName());
                    }
            );
            tokenResponse.setRoles(roles);
            return new ResponseEntity(tokenResponse, HttpStatus.OK);
        }catch (Exception exception){
            throw exception;
        }
    }
}
