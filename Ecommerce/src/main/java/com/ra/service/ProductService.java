package com.ra.service;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findByKey(String key);
    Page<ProductResponse> findAllProductSellPageAndSort(String field, String direction, int page, int pageSize);
    Page<ProductResponse> findAllProductPageAndSort(String field, String direction, int page, int pageSize);
    ProductResponse add(ProductRequest request);
    ProductResponse put(long productId,ProductRequest request);
    ProductResponse findById(long productId);
    ProductResponse findByIdProductSell(long productId);
    ResponseEntity delete(long productId);
    List<ProductResponse> getNewProducts();
    List<ProductResponse> getFeaturedProducts();
    List<ProductResponse> getBestSellerProducts();
    List<ProductResponse> getBestSellerProductsInMonth();
    List<ProductResponse> findByCategoryId(long categoryId);
}
