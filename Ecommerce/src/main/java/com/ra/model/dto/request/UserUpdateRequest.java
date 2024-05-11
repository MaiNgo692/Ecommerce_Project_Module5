package com.ra.model.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String file;
}
