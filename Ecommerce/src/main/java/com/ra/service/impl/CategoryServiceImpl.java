package com.ra.service.impl;

import com.ra.model.dto.request.CategoryRequest;
import com.ra.model.dto.response.CategoryResponse;
import com.ra.model.entity.CategoryEntity;
import com.ra.repository.CategoryRepository;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryResponse> findCategoryActive() {
        List<CategoryEntity> categoryEntities = categoryRepository.findCategoryEntitiesByStatusTrue();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        categoryEntities.forEach(categoryEntity -> {
            CategoryResponse categoryResponse = convertToCategoryResponse(categoryEntity);
            categoryResponses.add(categoryResponse);
        });
        return categoryResponses;
    }
    @Override
    public Page<CategoryResponse> findAllCategoryPageAndSort(String field, String direction, int page, int pageSize){
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        Pageable pageable = (page == 1)? PageRequest.of(page-1, pageSize, sort)
                :PageRequest.of(page-1, pageSize);
        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAll(pageable);
        return categoryEntityPage.map(this::convertToCategoryResponse);
    }
    @Override
    public   CategoryResponse findById(long categoryId){
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();
        return convertToCategoryResponse(categoryEntity);
    }
    @Override
    public CategoryResponse add(CategoryRequest categoryRequest){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryRequest.getCategoryName());
        categoryEntity.setDescription(categoryRequest.getDescription());
        categoryEntity.setStatus(true);
        categoryRepository.save(categoryEntity);
        return convertToCategoryResponse(categoryEntity);
    }
    @Override
    public CategoryResponse update(long categoryId,CategoryRequest categoryRequest){
        CategoryEntity categoryEntity = categoryRepository.getById(categoryId);
        categoryEntity.setCategoryName(categoryRequest.getCategoryName());
        categoryEntity.setDescription(categoryRequest.getDescription());
        categoryRepository.save(categoryEntity);
        return convertToCategoryResponse(categoryEntity);
    }
    @Override
    public void delete(long categoryId){
        CategoryEntity categoryEntity = categoryRepository.getById(categoryId);
        categoryRepository.delete(categoryEntity);
    }
    private CategoryResponse convertToCategoryResponse(CategoryEntity categoryEntity){
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(categoryEntity.getCategoryId());
        categoryResponse.setCategoryName(categoryEntity.getCategoryName());
        categoryResponse.setDescription(categoryEntity.getDescription());
        return categoryResponse;
    }
}
