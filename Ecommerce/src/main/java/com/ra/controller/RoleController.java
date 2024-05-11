package com.ra.controller;

import com.ra.model.entity.RoleEntity;
import com.ra.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/admin/roles")
    @ResponseBody
    public Map<String, Object> getRoles() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", roleEntityList);
        return rtn;
    }
}
