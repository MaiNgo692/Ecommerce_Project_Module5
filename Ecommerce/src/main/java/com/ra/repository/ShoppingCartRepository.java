package com.ra.repository;

import com.ra.model.entity.ShopingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShopingCartEntity,Integer> {
    List<ShopingCartEntity> findShopingCartEntitiesByUserId(Long userId);
    ShopingCartEntity findShopingCartEntityByUserIdAndShopingCartId(Long userId,int cartId);
}
