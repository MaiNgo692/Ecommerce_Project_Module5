package com.ra.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsEntityPK implements Serializable {
    @Id
    @jakarta.persistence.Column(name = "order_id", nullable = false)
    private long orderId;
    @Id
    @jakarta.persistence.Column(name = "product_id", nullable = false)
    private long productId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsEntityPK that = (OrderDetailsEntityPK) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
