package com.ra.service.impl;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.ProductRequest;
import com.ra.model.dto.response.ProductResponse;
import com.ra.model.entity.OrderDetailsEntity;
import com.ra.model.entity.OrdersEntity;
import com.ra.model.entity.ProductsEntity;
import com.ra.model.entity.WishListEntity;
import com.ra.repository.OrderDetailRepository;
import com.ra.repository.OrderRepository;
import com.ra.repository.ProductRepository;
import com.ra.repository.WishListRepository;
import com.ra.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<ProductResponse> findByKey(String key) {
        List<ProductsEntity> productsEntities = productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (key != null) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("productName"), "%" + key + "%"),
                        criteriaBuilder.like(root.get("description"), "%" + key + "%")
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        List<ProductResponse> responses = new ArrayList<>();
        productsEntities.forEach(products -> {
            responses.add(convertToProductResponse(products));
        });
        return responses;
    }
    @Override
    public Page<ProductResponse> findAllProductSellPageAndSort(String field, String direction, int page, int pageSize) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        Pageable pageable = (page == 1)? PageRequest.of(page-1, pageSize, sort)
                :PageRequest.of(page-1, pageSize);
        Page<ProductsEntity> productsEntityPage = productRepository.findProductsEntitiesByStockQuantityGreaterThan(1,pageable);
        Page<ProductResponse> productResponses = productsEntityPage.map(this::convertToProductResponse);
        return productResponses;
    }
    @Override
    public ProductResponse add(ProductRequest request) {
        ProductsEntity productsEntity = convertToProductEntity(new ProductsEntity(), request);
        productRepository.save(productsEntity);
        return convertToProductResponse(productsEntity);
    }
    @Override
    public ProductResponse put(long productId,ProductRequest request){
        ProductsEntity productsEntity= productRepository.getById(productId);
        ProductsEntity productsEntity1 = convertToProductEntity(productsEntity,request);
        productRepository.save(productsEntity1);
        return convertToProductResponse(productsEntity1);
    }
    @Override
    public Page<ProductResponse> findAllProductPageAndSort(String field, String direction, int page, int pageSize){
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        Pageable pageable = (page == 1)? PageRequest.of(page-1, pageSize, sort)
                :PageRequest.of(page-1, pageSize);
        Page<ProductsEntity> productsEntityPage = productRepository.findAll(pageable);
        Page<ProductResponse> productResponses = productsEntityPage.map(this::convertToProductResponse);
        return productResponses;
    }
    @Override
    public ProductResponse findById(long productId){
        ProductsEntity productsEntity = productRepository.findById(productId).get();
        return convertToProductResponse(productsEntity);
    }
    @Override
    public ProductResponse findByIdProductSell(long productId) {
        ProductsEntity productsEntity = productRepository.findProductsEntityByProductIdAndStockQuantityGreaterThan(productId,1);
        return convertToProductResponse(productsEntity);
    }
    @Override
    public ResponseEntity delete(long productId){
            ProductsEntity productsEntity = productRepository.findById(productId).get();
            if(productsEntity != null){
                productRepository.delete(productsEntity);
                return new ResponseEntity<>("\"message\" : \"delete success !\"", HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new BaseException("RA-00-02"),HttpStatus.NOT_FOUND);
            }

    }
    @Override
    public List<ProductResponse> getNewProducts() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, (cal.get(Calendar.DAY_OF_MONTH) - 2));
        List<ProductsEntity> productsEntities = productRepository.findProductsEntitiesByCreatedAtAfter(cal.getTime());
        List<ProductResponse> productResponses = new ArrayList<>();
        productsEntities.forEach(products -> productResponses.add(convertToProductResponse(products)));
        return productResponses;
    }
    @Override
    public List<ProductResponse> getFeaturedProducts() {
        List<WishListEntity> wishListEntities = wishListRepository.findAll();
        List<ProductsEntity> productsEntities = productRepository.findAll();
        Map<ProductsEntity,Integer> mapProductCount = new HashMap<>();
        productsEntities.forEach(productsEntity -> {
            int count =0;
            for(WishListEntity wishList: wishListEntities){
                if(productsEntity.getProductId() == wishList.getProductId()){
                    count+=1;
                }
            }
            mapProductCount.put(productsEntity,count);
        });
        return reverseMapProduct(mapProductCount);
    }
    @Override
    public List<ProductResponse> getBestSellerProducts() {
        List<OrderDetailsEntity> orderDetailsEntities = orderDetailRepository.findAll();
        Map<ProductsEntity,Integer> mapProductQuantity= new HashMap<>();
        List<ProductsEntity> productsEntities = productRepository.findAll();
        productsEntities.forEach(productsEntity -> {
            int quantity =0;
            for(OrderDetailsEntity orderDetail: orderDetailsEntities){
                if(productsEntity.getProductId() == orderDetail.getProductId()){
                    quantity+=orderDetail.getOrderQuantity();
                }
            }
            mapProductQuantity.put(productsEntity,quantity);
        });
        return reverseMapProduct(mapProductQuantity);
    }

    @Override
    public List<ProductResponse> getBestSellerProductsInMonth() {
        //lấy ngày cuối cùng của tháng trước
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        // Tìm tất cả các order tạo từ đầu tháng
        List<OrdersEntity> ordersEntities = orderRepository.findOrdersEntitiesByCreatedAtAfter(cal.getTime());
        List<OrderDetailsEntity> orderDetailsEntities = new ArrayList<>();
        // Duyệt tất cả các order để lấy toàn bộ orderDetail trong tháng
        ordersEntities.forEach(order -> {
            List<OrderDetailsEntity> orderDetails = orderDetailRepository.findOrderDetailsEntitiesByOrderId(order.getOrderId());
            orderDetailsEntities.addAll(orderDetails);
        });
        List<ProductsEntity> productsEntities = productRepository.findAll();
        Map<ProductsEntity,Integer> mapProductQuantity= new HashMap<>();
        // Duyệt tất cả orderDetail để lấy product và số lượng đặt hàng của từng sản phẩm
        productsEntities.forEach(productsEntity -> {
            int quantity =0;
            for(OrderDetailsEntity orderDetail:orderDetailsEntities){
                if(productsEntity.getProductId()==orderDetail.getProductId()){
                    quantity+=orderDetail.getOrderQuantity();
                }
            }
            mapProductQuantity.put(productsEntity,quantity);
        });
        return reverseMapProduct(mapProductQuantity);
    }

    @Override
    public List<ProductResponse> findByCategoryId(long categoryId) {
        List<ProductsEntity> productsEntities = productRepository.findProductsEntitiesByCategoryIdAndStockQuantityGreaterThan(categoryId,1);
        List<ProductResponse> productResponses = new ArrayList<>();
        productsEntities.forEach(products -> productResponses.add(convertToProductResponse(products)));
        return productResponses;
    }

    private ProductsEntity convertToProductEntity(ProductsEntity productsEntity,ProductRequest productRequest){
        productsEntity.setSku(productsEntity.getSku()==null?UUID.randomUUID().toString():productsEntity.getSku());
        productsEntity.setProductName(productRequest.getProductName());
        productsEntity.setDescription(productRequest.getDescription());
        productsEntity.setUnitPrice(productRequest.getUnitPrice());
        productsEntity.setStockQuantity(productRequest.getStock());
        productsEntity.setImage(productRequest.getImage());
        productsEntity.setCategoryId(productRequest.getCategoryId());
        if(productsEntity.getProductId()!=null){
            productsEntity.setUpdateAt(new Date());
        }else {
            productsEntity.setCreatedAt(new Date());
        }
        return productsEntity;
    }
    private ProductResponse convertToProductResponse(ProductsEntity product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setDescription(product.getDescription());
        productResponse.setUnitPrice(product.getUnitPrice());
        productResponse.setImageUrl(product.getImage());
        return productResponse;
    }
    private List<ProductResponse> reverseMapProduct(Map<ProductsEntity,Integer> mapProduct){
        // Sắp xếp product với số lượng từ lớn đến bé
        Map<ProductsEntity,Integer> sorted = mapProduct.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a,b)->a, LinkedHashMap::new)
                );
        List<ProductResponse> responses = new ArrayList<>();
        //paste sang dạng trả về
        sorted.forEach(new BiConsumer<ProductsEntity, Integer>() {
            @Override
            public void accept(ProductsEntity productsEntity, Integer integer) {
                responses.add(convertToProductResponse(productsEntity));
            }
        });
        return responses;
    }
}
