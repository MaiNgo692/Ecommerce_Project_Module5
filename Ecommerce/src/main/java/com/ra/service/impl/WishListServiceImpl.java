package com.ra.service.impl;

import com.ra.model.dto.response.ProductOfWishListResponse;
import com.ra.model.entity.ProductsEntity;
import com.ra.model.entity.WishListEntity;
import com.ra.repository.ProductRepository;
import com.ra.repository.WishListRepository;
import com.ra.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public boolean add(long userId, long productId) {
        WishListEntity wishList = new WishListEntity();
        wishList.setProductId(productId);
        wishList.setUserId(userId);
        wishListRepository.save(wishList);
        return true;
    }

    @Override
    public boolean delete(long userId, long wishListId) {
        WishListEntity wishList = wishListRepository.getWishListEntityByUserIdAndWishListId(userId,wishListId);
        wishListRepository.delete(wishList);
        return true;
    }

    @Override
    public List<ProductOfWishListResponse> getWishList(long userId) {
        List<WishListEntity> wishListEntities = wishListRepository.getWishListEntitiesByUserId(userId);
        List<ProductOfWishListResponse> responses = new ArrayList<>();
        wishListEntities.forEach(wishListEntity -> {
            ProductsEntity product = productRepository.findById(wishListEntity.getProductId()).get();
            ProductOfWishListResponse productOfWishListResponse = new ProductOfWishListResponse();
            productOfWishListResponse.setProductId(product.getProductId());
            productOfWishListResponse.setProductName(product.getProductName());
            productOfWishListResponse.setImageUrl(product.getImage());
            responses.add(productOfWishListResponse);
        });
        return responses;
    }
}
