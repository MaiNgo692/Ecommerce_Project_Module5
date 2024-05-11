package com.ra.repository;

import com.ra.model.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity,Long> {
    List<OrdersEntity> findOrdersEntitiesByUserId(long userId);
    List<OrdersEntity> findOrdersEntitiesByUserIdAndStatusContainingIgnoreCase(long userId,String status);
    OrdersEntity getOrdersEntityBySerialNumberAndUserId(String serialNumber,long userId);
    List<OrdersEntity> findOrdersEntitiesByStatus(String status);
    OrdersEntity getOrdersEntityByOrderId(long orderId);
    OrdersEntity getOrdersEntityBySerialNumberAndUserIdAndStatusEquals(String serialNumber,long userId,String status);

    List<OrdersEntity> findOrdersEntitiesByCreatedAtBetween(Date from,Date to);
    List<OrdersEntity> findOrdersEntitiesByCreatedAtAfter(Date endDateOfLastMonth);
}
