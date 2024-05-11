package com.ra.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TokenResponse {
    private long id;
    private String fullName;
    private String type = "Bearer Token";
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
}
