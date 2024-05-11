package com.ra.service;

import com.ra.model.dto.request.AddressAddRequest;
import com.ra.model.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse add(AddressAddRequest request,long userId);

    void delete(long addressId,long userId);

    List<AddressResponse> getAllAddress(long userId);

    AddressResponse getAddressById(long addressId,long userId);
}
