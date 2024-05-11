package com.ra.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CommonFunction<T> {
    public Map<String, Object> mapResponse(Page<T> responses, int pageNumber, int pageSize, String sortField, String sortType) {
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses.getContent());
        rtn.put("totalPages", responses.getTotalPages());
        rtn.put("number", pageNumber);
        rtn.put("size", pageSize);
        rtn.put("sort", sortField + "," + sortType);
        return rtn;
    }
}
