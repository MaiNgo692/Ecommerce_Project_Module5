package com.ra.repository;

import com.ra.model.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity,Long> {
    List<WishListEntity> getWishListEntitiesByUserId(long userId);
    WishListEntity getWishListEntityByUserIdAndWishListId(long userId,long wishListId);
}
