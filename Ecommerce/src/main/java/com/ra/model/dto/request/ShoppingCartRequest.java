package com.ra.model.dto.request;

import lombok.Data;

@Data
public class ShoppingCartRequest {
    private Long productId;
    private int quantity;
}
