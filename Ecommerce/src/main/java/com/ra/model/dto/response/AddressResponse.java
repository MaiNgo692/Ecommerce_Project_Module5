package com.ra.model.dto.response;

import lombok.Data;

@Data
public class AddressResponse {
    private long addressId;
    private String receiveName;
    private String fullAddress;
    private String phone;
}
