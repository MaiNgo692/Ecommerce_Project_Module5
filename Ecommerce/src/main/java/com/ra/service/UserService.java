package com.ra.service;

import com.ra.model.dto.request.ChangePasswordRequest;
import com.ra.model.dto.request.UserRequest;
import com.ra.model.dto.request.UserUpdateRequest;
import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity add(UserRequest request);
    UsersEntity activeInActiveUser(long userId);
    UserResponse findByUserId(long userId);
    UserResponse update(UserUpdateRequest request,long userId);
    ResponseEntity changePassword(ChangePasswordRequest request, long userId);
    Page<UserResponse> findAllPageAndSort(String field, String direction, int page, int pageSize);
    Page<UserResponse> findByNamePageAndSort(String searchName,String field, String direction, int page, int pageSize);
}
