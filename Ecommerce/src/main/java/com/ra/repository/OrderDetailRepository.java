package com.ra.repository;

import com.ra.model.entity.OrderDetailsEntity;
import com.ra.model.entity.OrderDetailsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailsEntity, OrderDetailsEntityPK> {

    List<OrderDetailsEntity> findOrderDetailsEntitiesByOrderId(long orderId);
}
