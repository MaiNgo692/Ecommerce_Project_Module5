package com.ra.service;

import com.ra.model.dto.request.CategoryRequest;
import com.ra.model.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findCategoryActive();
    CategoryResponse findById(long categoryId);
    CategoryResponse add(CategoryRequest categoryRequest);

    void delete(long categoryId);
    CategoryResponse update(long categoryId,CategoryRequest categoryRequest);
    Page<CategoryResponse> findAllCategoryPageAndSort(String field, String direction, int page, int pageSize);
}
