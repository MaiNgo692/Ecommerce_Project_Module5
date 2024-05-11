package com.ra.service.impl;

import com.ra.model.dto.response.OrderDetailResponse;
import com.ra.model.dto.response.OrderResponse;
import com.ra.model.dto.response.ProductOfOrderDetailResponse;
import com.ra.model.dto.response.SalesByCategoryResponse;
import com.ra.model.entity.CategoryEntity;
import com.ra.model.entity.OrderDetailsEntity;
import com.ra.model.entity.OrdersEntity;
import com.ra.model.entity.ProductsEntity;
import com.ra.repository.CategoryRepository;
import com.ra.repository.OrderDetailRepository;
import com.ra.repository.OrderRepository;
import com.ra.repository.ProductRepository;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<OrderResponse> getOrderByUserId(long userId) {
        List<OrdersEntity> ordersEntities = orderRepository.findOrdersEntitiesByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        ordersEntities.forEach(orders -> orderResponses.add(pasteToOrderResponse(orders)));
        return orderResponses;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<OrdersEntity> ordersEntities = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        ordersEntities.forEach(orders -> orderResponses.add(pasteToOrderResponse(orders)));
        return orderResponses;
    }

    @Override
    public OrderDetailResponse getOderDetailBySerialNumber(String serialNumber, long userId) {
        OrdersEntity order = orderRepository.getOrdersEntityBySerialNumberAndUserId(serialNumber,userId);
        return pasteToOrderDetailResponse(order);
    }

    @Override
    public OrderDetailResponse getOderDetailByOrderId(long orderId) {
        OrdersEntity order = orderRepository.getOrdersEntityByOrderId(orderId);
        return pasteToOrderDetailResponse(order);
    }

    @Override
    public List<OrderResponse> getOrderByUserIdAndStatus(long userId, String status) {
        List<OrdersEntity> ordersEntities = orderRepository.findOrdersEntitiesByUserIdAndStatusContainingIgnoreCase(userId,status);
        List<OrderResponse> responses = new ArrayList<>();
        ordersEntities.forEach(orders -> responses.add(pasteToOrderResponse(orders)));
        return responses;
    }

    @Override
    public List<OrderResponse> getAllOrderByStatus(String status) {
        List<OrdersEntity> ordersEntities = orderRepository.findOrdersEntitiesByStatus(status);
        List<OrderResponse> responses = new ArrayList<>();
        ordersEntities.forEach(orders -> responses.add(pasteToOrderResponse(orders)));
        return responses;
    }

    @Override
    public boolean cancelOrder(long userId, String serialNumber) {
        OrdersEntity order = orderRepository.getOrdersEntityBySerialNumberAndUserIdAndStatusEquals(serialNumber,userId,"WAITING");
        if(order!=null){
            List<OrderDetailsEntity> orderDetailsEntities = orderDetailRepository.findOrderDetailsEntitiesByOrderId(order.getOrderId());
            orderDetailsEntities.forEach(orderDetailsEntity -> {
                ProductsEntity product = productRepository.findById(orderDetailsEntity.getProductId()).get();
                product.setStockQuantity(product.getStockQuantity()+orderDetailsEntity.getOrderQuantity());
                product.setUpdateAt(new Date());
            });
            order.setStatus("CANCEL");
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public OrderResponse changeOrderStatus(long orderId, String orderStatus) {
        OrdersEntity order = orderRepository.getOrdersEntityByOrderId(orderId);
        order.setStatus(orderStatus);
        orderRepository.save(order);
        return pasteToOrderResponse(order);
    }

    @Override
    public BigDecimal getSalesPeriodTime(Date from, Date to) {
        List<OrdersEntity> orders = orderRepository.findOrdersEntitiesByCreatedAtBetween(from,to);
        BigDecimal sales = new BigDecimal(0);
        for(OrdersEntity order : orders){
            sales = sales.add(order.getTotalPrice());
        }
        return sales;
    }

    @Override
    public List<SalesByCategoryResponse> getSalesByCategory(Date from, Date to) {
        List<OrdersEntity> orders = orderRepository.findOrdersEntitiesByCreatedAtBetween(from,to);
        List<SalesByCategoryResponse> responses = new ArrayList<>();

        List<CategoryEntity> categories = categoryRepository.findAll();
        categories.forEach(category -> {
            SalesByCategoryResponse sales = new SalesByCategoryResponse();
            sales.setCategoryName(category.getCategoryName());
            BigDecimal salesValue = new BigDecimal(0);
            for(OrdersEntity order: orders){
                List<OrderDetailsEntity> orderDetailsEntities = orderDetailRepository.findOrderDetailsEntitiesByOrderId(order.getOrderId());
                for(OrderDetailsEntity orderDetail : orderDetailsEntities){
                    ProductsEntity productsEntity = productRepository.findById(orderDetail.getProductId()).get();
                    if(productsEntity.getCategoryId() == category.getCategoryId()){
                        BigDecimal sale = orderDetail.getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getOrderQuantity()));
                        salesValue = salesValue.add(sale);
                    }
                }
            }
            sales.setSales(salesValue);
            responses.add(sales);
        });
        return responses;
    }
    private OrderDetailResponse pasteToOrderDetailResponse(OrdersEntity order){
        OrderDetailResponse orderResponse = new OrderDetailResponse();
        orderResponse.setSerialNumber(order.getSerialNumber());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setNote(order.getNote());
        orderResponse.setReceiveName(order.getReceiveName());
        orderResponse.setReceiveAddress(order.getReceiveAddress());
        orderResponse.setStatus(getStatus(order.getStatus()));
        orderResponse.setCreatedAt(new SimpleDateFormat("dd-MM-yyyy").format(order.getCreatedAt()));
        orderResponse.setReceivedAt(new SimpleDateFormat("dd-MM-yyyy").format(order.getReceiveAt()));
        List<OrderDetailsEntity> orderDetailsEntities = orderDetailRepository.findOrderDetailsEntitiesByOrderId(order.getOrderId());
        List<ProductOfOrderDetailResponse> productOfOrderDetailResponses = new ArrayList<>();
        orderDetailsEntities.forEach(orderDetailsEntity -> {
            ProductsEntity productsEntity = productRepository.getById(orderDetailsEntity.getProductId());
            ProductOfOrderDetailResponse product = new ProductOfOrderDetailResponse();
            product.setProductName(productsEntity.getProductName());
            product.setImageUrl(productsEntity.getImage());
            product.setUnitPrice(productsEntity.getUnitPrice());
            product.setQuantity(orderDetailsEntity.getOrderQuantity());
            productOfOrderDetailResponses.add(product);
        });
        orderResponse.setListItem(productOfOrderDetailResponses);
        return orderResponse;
    }
    private OrderResponse  pasteToOrderResponse(OrdersEntity order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setSerialNumber(order.getSerialNumber());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setNote(order.getNote());
        orderResponse.setStatus(getStatus(order.getStatus()));
        orderResponse.setCreatedAt(new SimpleDateFormat("dd-MM-yyyy").format(order.getCreatedAt()));
        return orderResponse;
    }
    private String getStatus(String status){
        switch (status){
            case "WAITING":
                return "Đơn hàng mới chờ xác nhận";
            case "CONFIRM":
                return "Đã xác nhận";
            case "DELIVERY":
                return "Đang giao hàng";
            case "SUCCESS":
                return "Đã giao hàng";
            case "CANCEL":
                return "Đã hủy đơn";
            default:
                return "Bị từ chối";
        }
    }
}
