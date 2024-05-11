package com.ra.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Table(name = "shoping_cart", schema = "ecommerce", catalog = "")
public class ShopingCartEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shoping_cart_id", nullable = false)
    private int shopingCartId;
    @Basic
    @Column(name = "product_id", nullable = true)
    private Long productId;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;
    @Basic
    @Column(name = "order_quantity", nullable = true)
    @Min(value = 1,message = "{value.min.error}")
    private Integer orderQuantity;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "productId", referencedColumnName = "product_id", nullable = false)
//    private ProductsEntity productsByProductId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
//    private UsersEntity usersByUserId;

//    public ShopingCartEntity(int shopingCartId, Long productId, Long userId, Integer orderQuantity) {
//        this.shopingCartId = shopingCartId;
//        this.productId = productId;
//        this.userId = userId;
//        this.orderQuantity = orderQuantity;
//        this.productsByProductId = new ProductsEntity(productId);
//        this.usersByUserId=new UsersEntity(userId);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopingCartEntity that = (ShopingCartEntity) o;
        return shopingCartId == that.shopingCartId && Objects.equals(productId, that.productId) && Objects.equals(userId, that.userId) && Objects.equals(orderQuantity, that.orderQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopingCartId, productId, userId, orderQuantity);
    }
}
