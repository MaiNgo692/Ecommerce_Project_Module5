package com.ra.model.dto.response;

import lombok.Data;

@Data
public class ProductOfWishListResponse {
    private long productId;
    private String productName;
    private String imageUrl;
}
