package com.ra.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponse {
    private String serialNumber;
    private BigDecimal totalPrice;
    private String note;
    private String status;
    private String createdAt;
}
