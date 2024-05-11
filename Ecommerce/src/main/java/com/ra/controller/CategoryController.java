package com.ra.controller;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.CategoryRequest;
import com.ra.model.dto.response.CategoryResponse;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommonFunction<CategoryResponse> commonFunction;

    @GetMapping("/categories")
    @ResponseBody
    public Map<String, Object> get() {
        Map<String, Object> rtn = new LinkedHashMap<>();
        List<CategoryResponse> categoryEntities = categoryService.findCategoryActive();
        rtn.put("data", categoryEntities);
        return rtn;
    }

    @GetMapping("/admin/categories")
    @ResponseBody
    public Map<String, Object> getAll(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                      @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
                                      @RequestParam(name = "sort_by") String sortField,
                                      @RequestParam(name = "how_sort") String sortType) {
        Page<CategoryResponse> categoryEntities = categoryService.findAllCategoryPageAndSort(sortField, sortType, pageNumber, pageSize);
        return commonFunction.mapResponse(categoryEntities, pageNumber, pageSize, sortField, sortType);
    }

    @GetMapping("/admin/categories/{categoryId}")
    @ResponseBody
    public Map<String, Object> getCategoryById(@PathVariable("categoryId") long categoryId) {
        Map<String, Object> rtn = new LinkedHashMap<>();
        CategoryResponse response = categoryService.findById(categoryId);
        rtn.put("data", response);
        return rtn;
    }

    @RequestMapping(value = "/admin/categories", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(@RequestBody CategoryRequest request) {
        Map<String, Object> rtn = new LinkedHashMap<>();
        CategoryResponse response = categoryService.add(request);
        rtn.put("data", response);
        return rtn;
    }

    @RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> update(@RequestBody CategoryRequest request, @PathVariable("categoryId") long categoryId) {
        Map<String, Object> rtn = new LinkedHashMap<>();
        CategoryResponse response = categoryService.update(categoryId, request);
        rtn.put("data", response);
        return rtn;
    }

    @RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("categoryId") long categoryId) {
        Map<String, Object> rtn = new LinkedHashMap<>();
        categoryService.delete(categoryId);
        rtn.put("message", new BaseException("RA-01-02").getMessage());
        return rtn;
    }
}
