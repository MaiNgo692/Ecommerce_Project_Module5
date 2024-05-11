package com.ra.repository;

import com.ra.model.entity.CategoryEntity;
import com.ra.model.entity.ProductsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>, PagingAndSortingRepository<CategoryEntity, Long> {
    List<CategoryEntity> findCategoryEntitiesByStatusTrue();
    Page<CategoryEntity> findAll(Pageable pageable);
}
