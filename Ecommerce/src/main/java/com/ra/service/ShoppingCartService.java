package com.ra.service;

import com.ra.model.dto.request.AddressRequest;
import com.ra.model.dto.request.ShoppingCartRequest;
import com.ra.model.dto.response.ShoppingCartResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartResponse> getAllShoppingCartByUserId(long userId);
    ShoppingCartResponse add(ShoppingCartRequest cartRequest,long userId);
    ShoppingCartResponse update(int quantity,long userId, int cartId);
    void delete(long userId, int cartId);
    void delete(long userId);
    ResponseEntity checkOut(AddressRequest addressRequest, long userId);
}
