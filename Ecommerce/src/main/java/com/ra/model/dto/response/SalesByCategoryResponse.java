package com.ra.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesByCategoryResponse {
    private String categoryName;
    private BigDecimal sales;
}
