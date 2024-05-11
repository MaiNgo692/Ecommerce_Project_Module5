package com.ra.model.dto.request;

import lombok.Data;

@Data
public class AddressAddRequest {
    private String receiveName;
    private String fullAddress;
    private String phone;
}
