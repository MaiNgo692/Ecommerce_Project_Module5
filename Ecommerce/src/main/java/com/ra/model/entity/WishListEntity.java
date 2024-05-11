package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wish_list", schema = "ecommerce", catalog = "")
public class WishListEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wish_list_id", nullable = false)
    private long wishListId;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;
    @Basic
    @Column(name = "product_id", nullable = true)
    private Long productId;
//    @ManyToOne
//    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
//    private UsersEntity usersByUserId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "productId", referencedColumnName = "product_id",nullable = false)
//    private ProductsEntity productsByProductId;

//    public WishListEntity(long wishListId, Long userId, Long productId) {
//        this.wishListId = wishListId;
//        this.userId = userId;
//        this.productId = productId;
//        this.usersByUserId=new UsersEntity(userId);
//        this.productsByProductId=new ProductsEntity(productId);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishListEntity that = (WishListEntity) o;
        return wishListId == that.wishListId && Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishListId, userId, productId);
    }
}
