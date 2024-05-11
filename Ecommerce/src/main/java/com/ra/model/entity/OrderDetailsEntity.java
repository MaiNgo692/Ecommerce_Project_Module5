package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "order_details", schema = "ecommerce", catalog = "")
@IdClass(OrderDetailsEntityPK.class)
public class OrderDetailsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "order_id", nullable = false)
    private long orderId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "product_id", nullable = false)
    private long productId;
    @Basic
    @Column(name = "name", nullable = true, length = 100)
    private String name;
    @Basic
    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;
    @Basic
    @Column(name = "order_quantity", nullable = true)
    private Integer orderQuantity;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "productId", referencedColumnName = "product_id",nullable = false)
//    private ProductsEntity productsByProductId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orderId", referencedColumnName = "order_id",nullable = false)
//    private OrdersEntity ordersByOrderId;

//    public OrderDetailsEntity(long orderId, long productId, String name, BigDecimal unitPrice, Integer orderQuantity) {
//        this.orderId = orderId;
//        this.productId = productId;
//        this.name = name;
//        this.unitPrice = unitPrice;
//        this.orderQuantity = orderQuantity;
//        this.productsByProductId = new ProductsEntity(productId);
//        this.ordersByOrderId = new OrdersEntity(orderId);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsEntity that = (OrderDetailsEntity) o;
        return orderId == that.orderId && productId == that.productId && Objects.equals(name, that.name) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(orderQuantity, that.orderQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, name, unitPrice, orderQuantity);
    }
}
