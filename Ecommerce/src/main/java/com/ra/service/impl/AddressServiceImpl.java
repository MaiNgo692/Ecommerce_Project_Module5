package com.ra.service.impl;

import com.ra.model.dto.request.AddressAddRequest;
import com.ra.model.dto.response.AddressResponse;
import com.ra.model.entity.AddressEntity;
import com.ra.repository.AddressRepository;
import com.ra.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public AddressResponse add(AddressAddRequest request,long userId) {
        AddressEntity address = pasteToAddressEntity(request,userId);
        addressRepository.save(address);
        return pasteToAddressResponse(address);
    }

    @Override
    public void delete(long addressId, long userId) {
        AddressEntity address = addressRepository.findAddressEntityByAddressIdAndUserId(addressId,userId);
        addressRepository.delete(address);
    }

    @Override
    public List<AddressResponse> getAllAddress(long userId) {
        List<AddressEntity> addressEntities = addressRepository.findAddressEntitiesByUserId(userId);
        List<AddressResponse> addressResponses = new ArrayList<>();
        addressEntities.forEach(addressEntity -> addressResponses.add(pasteToAddressResponse(addressEntity)));
        return addressResponses;
    }

    @Override
    public AddressResponse getAddressById(long addressId, long userId) {
        AddressEntity address = addressRepository.findAddressEntityByAddressIdAndUserId(addressId,userId);
        return pasteToAddressResponse(address);
    }

    private AddressEntity pasteToAddressEntity(AddressAddRequest request,long userId){
        AddressEntity address = new AddressEntity();
        address.setUserId(userId);
        address.setFullAddress(request.getFullAddress());
        address.setPhone(request.getPhone());
        address.setReceiveName(request.getReceiveName());
        return address;
    }
    private AddressResponse pasteToAddressResponse(AddressEntity addressEntity){
        AddressResponse address = new AddressResponse();
        address.setAddressId(addressEntity.getAddressId());
        address.setFullAddress(addressEntity.getFullAddress());
        address.setPhone(addressEntity.getPhone());
        address.setReceiveName(addressEntity.getReceiveName());
        return address;
    }
}
