package com.ra.model.dto.request;

import lombok.Data;

@Data
public class TokenRequest {
    private String username;
    private String password;
}
