package com.ra.service.impl;

import com.ra.model.entity.RoleEntity;
import com.ra.repository.RoleRepository;
import com.ra.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository repository;
    @Override
    public RoleEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public RoleEntity findByRoleName(String roleName) {
        return repository.findByRoleName(roleName);
    }
}
