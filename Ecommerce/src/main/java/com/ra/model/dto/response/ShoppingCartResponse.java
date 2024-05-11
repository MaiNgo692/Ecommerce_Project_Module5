package com.ra.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingCartResponse {
    private long id;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
}
