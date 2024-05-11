package com.ra.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductOfOrderDetailResponse {
    private String  productName;
    private String imageUrl;
    private BigDecimal unitPrice;
    private int quantity;
}
