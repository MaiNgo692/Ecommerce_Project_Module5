package com.ra.controller;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.AddressAddRequest;
import com.ra.model.dto.response.AddressResponse;
import com.ra.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1/user/account/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping
    public Map<String, Object> addAddress(@RequestBody AddressAddRequest request,@RequestParam("userId") long userId){
        AddressResponse response = addressService.add(request,userId);
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data",response);
        return rtn;
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity delete(@PathVariable("addressId")long addressId, @RequestParam("userId") long userId){
        addressService.delete(addressId,userId);
        return new ResponseEntity<>(new BaseException("RA-01-02").getErrorMessage(), HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public Map<String,Object> getAllAddress( @RequestParam("userId") long userId){
        List<AddressResponse> responseList = addressService.getAllAddress(userId);
        Map<String,Object> rtn = new HashMap<>();
        rtn.put("data", responseList);
        return rtn;
    }
    @RequestMapping(value = "/{addressId}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAddressById(@PathVariable("addressId")long addressId, @RequestParam("userId") long userId){
        AddressResponse  response = addressService.getAddressById(addressId,userId);
        Map<String,Object> rtn = new HashMap<>();
        rtn.put("data", response);
        return rtn;
    }
}
