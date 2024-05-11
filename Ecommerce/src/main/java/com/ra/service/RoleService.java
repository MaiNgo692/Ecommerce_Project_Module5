package com.ra.service;

import com.ra.model.entity.RoleEntity;

public interface RoleService {
    RoleEntity findById(Long id);

    RoleEntity findByRoleName(String roleName);
}
