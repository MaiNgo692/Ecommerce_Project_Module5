package com.ra.model.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String userName;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String phone;
    private String address;

}
