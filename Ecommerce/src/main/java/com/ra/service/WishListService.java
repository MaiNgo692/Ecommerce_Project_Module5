package com.ra.service;

import com.ra.model.dto.response.ProductOfWishListResponse;

import java.util.List;

public interface WishListService {
    boolean add(long userId,long productId);
    boolean delete(long userId,long wishListId);
    List<ProductOfWishListResponse> getWishList(long userId);
}
