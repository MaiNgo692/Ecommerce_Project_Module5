package com.ra.repository;

import com.ra.model.dto.request.UserRoleRequest;
import com.ra.model.entity.UserRoleEntity;
import com.ra.model.entity.UserRoleEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleRequest, UserRoleEntityPK> {
    UserRoleRequest findUserRoleRequestByRoleIdAndAndUserId(long roleId,long userId);
}
