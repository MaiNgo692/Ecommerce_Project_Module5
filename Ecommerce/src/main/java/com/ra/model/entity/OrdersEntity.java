package com.ra.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders", schema = "ecommerce", catalog = "")
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id", nullable = false)
    private long orderId;
    @Basic
    @Column(name = "serial_number", nullable = true, length = 100)
    private String serialNumber;
    @Basic
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Basic
    @Column(name = "total_price", nullable = true, precision = 2)
    private BigDecimal totalPrice;
    @Basic
    @Column(name = "status", nullable = true)
    private String status;
    @Basic
    @Column(name = "note", nullable = true, length = 100)
    private String note;
    @Basic
    @Column(name = "receive_name", nullable = true, length = 100)
    private String receiveName;
    @Basic
    @Column(name = "receive_address", nullable = true, length = 255)
    private String receiveAddress;
    @Basic
    @Column(name = "receive_phone", nullable = true, length = 15)
    private String receivePhone;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Date createdAt = new Date();
    @Basic
    @Column(name = "receive_at", nullable = true)
    private Date receiveAt = new Date(createdAt.getTime() + ( 3600 * 1000 * 24 * 4));
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
//    private UsersEntity usersByUserId;
//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "ordersByOrderId")
//    private List<OrderDetailsEntity> orderDetailsEntities;

    public OrdersEntity(long orderId) {
        this.orderId = orderId;
    }

//    public OrdersEntity(long orderId, String serialNumber, long userId, BigDecimal totalPrice, String status, String note, String receiveName, String receiveAddress, String receivePhone, Date createdAt, Date receiveAt) {
//        this.orderId = orderId;
//        this.serialNumber = serialNumber;
//        this.userId = userId;
//        this.totalPrice = totalPrice;
//        this.status = status;
//        this.note = note;
//        this.receiveName = receiveName;
//        this.receiveAddress = receiveAddress;
//        this.receivePhone = receivePhone;
//        this.createdAt = createdAt;
//        this.receiveAt = receiveAt;
//        this.usersByUserId = new UsersEntity(userId);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return orderId == that.orderId && userId == that.userId && Objects.equals(serialNumber, that.serialNumber) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(status, that.status) && Objects.equals(note, that.note) && Objects.equals(receiveName, that.receiveName) && Objects.equals(receiveAddress, that.receiveAddress) && Objects.equals(receivePhone, that.receivePhone) && Objects.equals(createdAt, that.createdAt) && Objects.equals(receiveAt, that.receiveAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, serialNumber, userId, totalPrice, status, note, receiveName, receiveAddress, receivePhone, createdAt, receiveAt);
    }
}
