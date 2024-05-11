package com.ra.repository;

import com.ra.model.entity.ProductsEntity;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity,Long>, JpaSpecificationExecutor<ProductsEntity>, PagingAndSortingRepository<ProductsEntity, Long> {
    Page<ProductsEntity> findProductsEntitiesByStockQuantityGreaterThan(@Min(value = 1, message = "{value.min.error}") Integer stockQuantity, Pageable pageable);

    Page<ProductsEntity> findAll(Pageable pageable);
    ProductsEntity findProductsEntityByProductIdAndStockQuantityGreaterThan(long productId,@Min(value = 1, message = "{value.min.error}") Integer stockQuantity);

    List<ProductsEntity> findProductsEntitiesByCategoryIdAndStockQuantityGreaterThan(long categoryId,@Min(value = 1, message = "{value.min.error}") Integer stockQuantity);
    List<ProductsEntity> findProductsEntitiesByCreatedAtAfter(Date yesterDayDate);

}
