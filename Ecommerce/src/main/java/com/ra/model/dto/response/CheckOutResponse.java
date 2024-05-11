package com.ra.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckOutResponse {
    private String serialNumber;
    private BigDecimal totalPrice;
    private String status;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private String createdAt;
    private String receivedAt;
}
