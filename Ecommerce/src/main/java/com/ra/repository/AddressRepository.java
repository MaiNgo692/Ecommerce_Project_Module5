package com.ra.repository;

import com.ra.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    AddressEntity findAddressEntityByAddressIdAndUserId(long addressId,long userId);
    List<AddressEntity> findAddressEntitiesByUserId(long userId);
}
