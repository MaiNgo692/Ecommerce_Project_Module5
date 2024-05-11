package com.ra.service.impl;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.AddressRequest;
import com.ra.model.dto.request.ShoppingCartRequest;
import com.ra.model.dto.response.CheckOutResponse;
import com.ra.model.dto.response.ShoppingCartResponse;
import com.ra.model.entity.*;
import com.ra.repository.*;
import com.ra.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public List<ShoppingCartResponse> getAllShoppingCartByUserId(long userId) {
        List<ShopingCartEntity> cartEntities = cartRepository.findShopingCartEntitiesByUserId(userId);
        List<ShoppingCartResponse> cartResponses = new ArrayList<>();
        cartEntities.forEach(cart ->{
            ShoppingCartResponse cartResponse = convertCartEntityToCartResponse(cart);
            cartResponses.add(cartResponse);
        });
        return cartResponses;
    }

    @Override
    public ShoppingCartResponse add(ShoppingCartRequest cartRequest,long userId) {
        List<ShopingCartEntity> shopingCarts = cartRepository.findShopingCartEntitiesByUserId(userId);
        ShopingCartEntity shopingCart = convertCartRequestToCartEntity(cartRequest,userId);
        ProductsEntity product = productRepository.findById(shopingCart.getProductId()).get();
        for (ShopingCartEntity cart : shopingCarts) {
            if(shopingCart.getProductId() == cart.getProductId()){
                cart.setOrderQuantity(cart.getOrderQuantity()+shopingCart.getOrderQuantity());
                cart = checkQuantity(product,cart);
                return convertCartEntityToCartResponse(cart);
            }

        }
        shopingCart = checkQuantity(product,shopingCart);
        return convertCartEntityToCartResponse(shopingCart);
    }
    private ShopingCartEntity checkQuantity(ProductsEntity product,ShopingCartEntity cart){
    if(cart.getOrderQuantity()>product.getStockQuantity()){
        throw new BaseException("RA-00-06");
    }else {
        cartRepository.save(cart);
        product.setStockQuantity(product.getStockQuantity()-cart.getOrderQuantity());
        product.setUpdateAt(new Date());
        productRepository.save(product);
        return cart;
    }
}
    @Override
    public ShoppingCartResponse update(int quantity, long userId, int cartId) {
        ShopingCartEntity shopingCart = cartRepository.findShopingCartEntityByUserIdAndShopingCartId(userId,cartId);
        shopingCart.setOrderQuantity(quantity);
        cartRepository.save(shopingCart);
        return convertCartEntityToCartResponse(shopingCart);
    }

    @Override
    public void delete(long userId, int cartId) {
        ShopingCartEntity shopingCart = cartRepository.findShopingCartEntityByUserIdAndShopingCartId(userId,cartId);
         cartRepository.delete(shopingCart);
    }

    @Override
    public void delete(long userId) {
        List<ShopingCartEntity> shopingCarts = cartRepository.findShopingCartEntitiesByUserId(userId);
        cartRepository.deleteAll(shopingCarts);
    }

    @Override
    public ResponseEntity checkOut(AddressRequest addressRequest, long userId) {
        AddressEntity address = addressRepository.findAddressEntityByAddressIdAndUserId(addressRequest.getAddressId(),userId);
        List<ShopingCartEntity> shopingCarts = cartRepository.findShopingCartEntitiesByUserId(userId);
        OrdersEntity order = new OrdersEntity();
        order.setSerialNumber(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setStatus("WAITING");
        order.setUserId(userId);
        order.setReceiveName(address.getReceiveName());
        order.setReceiveAddress(address.getFullAddress());
        order.setReceivePhone(address.getPhone());
        orderRepository.save(order);
        BigDecimal totalPrice = new BigDecimal(0);
        List<OrderDetailsEntity> orderDetailsEntities= new ArrayList<>();
        for(ShopingCartEntity cart: shopingCarts){
            ProductsEntity product = productRepository.findById(cart.getProductId()).get();
            // Tính tổng tiền
            BigDecimal unitPrice = product.getUnitPrice();
            BigDecimal quantity = BigDecimal.valueOf(cart.getOrderQuantity());
            BigDecimal price = unitPrice.multiply(quantity);
            totalPrice = totalPrice.add(price);
            // Tạo oderDetail
            OrderDetailsEntity orderDetail = new OrderDetailsEntity();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setProductId(cart.getProductId());
            orderDetail.setName(product.getProductName());
            orderDetail.setUnitPrice(product.getUnitPrice());
            orderDetail.setOrderQuantity(cart.getOrderQuantity());
            orderDetailsEntities.add(orderDetail);
        }
        order.setTotalPrice(totalPrice);
        try{
            orderDetailRepository.saveAll(orderDetailsEntities);
            orderRepository.save(order);
            cartRepository.deleteAll(shopingCarts);
        }catch (Exception exception){
            orderDetailRepository.deleteAll(orderDetailsEntities);
            orderRepository.delete(order);
            return new ResponseEntity<>(new BaseException("RA-00-04").getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        CheckOutResponse checkOutResponse = new CheckOutResponse();
        checkOutResponse.setSerialNumber(order.getSerialNumber());
        checkOutResponse.setTotalPrice(order.getTotalPrice());
        checkOutResponse.setStatus(order.getStatus());
        checkOutResponse.setNote(order.getNote());
        checkOutResponse.setReceiveName(address.getReceiveName());
        checkOutResponse.setReceiveAddress(address.getFullAddress());
        checkOutResponse.setReceivePhone(address.getPhone());
        checkOutResponse.setCreatedAt(new SimpleDateFormat("dd-MM-yyyy").format(order.getCreatedAt()));
        checkOutResponse.setReceivedAt(new SimpleDateFormat("dd-MM-yyyy").format(order.getReceiveAt()));
        return ResponseEntity.ok(checkOutResponse) ;
    }

    private ShopingCartEntity convertCartRequestToCartEntity(ShoppingCartRequest cartRequest,long userId){
        ShopingCartEntity shopingCart = new ShopingCartEntity();
        shopingCart.setUserId(userId);
        shopingCart.setProductId(cartRequest.getProductId());
        shopingCart.setOrderQuantity(cartRequest.getQuantity());
        return shopingCart;
    }
    private ShoppingCartResponse convertCartEntityToCartResponse(ShopingCartEntity cartEntity){
        ShoppingCartResponse cartResponse = new ShoppingCartResponse();
        cartResponse.setId(cartEntity.getShopingCartId());
        cartResponse.setProductName(productRepository.findById(cartEntity.getProductId()).get().getProductName());
        cartResponse.setUnitPrice(productRepository.findById(cartEntity.getProductId()).get().getUnitPrice());
        cartResponse.setQuantity(cartEntity.getOrderQuantity());
        return cartResponse;
    }
}
