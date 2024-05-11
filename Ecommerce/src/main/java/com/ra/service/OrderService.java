package com.ra.service;

import com.ra.model.dto.response.OrderDetailResponse;
import com.ra.model.dto.response.OrderResponse;
import com.ra.model.dto.response.SalesByCategoryResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrderByUserId(long userId);
    List<OrderResponse> getAllOrder();
    OrderDetailResponse getOderDetailBySerialNumber(String serialNumber,long userId);
    OrderDetailResponse getOderDetailByOrderId(long orderId);
    List<OrderResponse> getOrderByUserIdAndStatus(long userId,String status);
    List<OrderResponse> getAllOrderByStatus(String status);
    boolean cancelOrder(long userId,String serialNumber);
    OrderResponse changeOrderStatus(long orderId,String orderStatus);

    BigDecimal getSalesPeriodTime(Date from, Date to);
    List<SalesByCategoryResponse> getSalesByCategory(Date from, Date to);
}
