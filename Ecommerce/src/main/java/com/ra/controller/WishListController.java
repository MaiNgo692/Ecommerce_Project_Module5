package com.ra.controller;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.WishRequest;
import com.ra.model.dto.response.ProductOfWishListResponse;
import com.ra.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api.myservice.com/v1/user/wish-list")
public class WishListController {
    @Autowired
    private WishListService wishListService;
    @PostMapping
    @ResponseBody
    public Map<String,String> addWishList(@RequestBody WishRequest request,@RequestParam("userId") long userId){
        Map<String,String> rtn = new HashMap<>();
        if(wishListService.add(userId,request.getProductId())){
            rtn.put("message",new BaseException("RA-01-05").getErrorMessage().getMessage());
        }else {
            rtn.put("message",new BaseException("RA-00-03").getErrorMessage().getMessage());
        }
        return rtn;
    }
    @GetMapping
    @ResponseBody
    public Map<String, Object> getWishList(@RequestParam("userId") long userId){
        List<ProductOfWishListResponse> responseList = wishListService.getWishList(userId);
        Map<String,Object> rtn = new HashMap<>();
        rtn.put("data",responseList);
        return rtn;
    }
    @DeleteMapping("/{wishListId}")
    @ResponseBody
    public Map<String,String> delete(@PathVariable("wishListId") long wishListId,@RequestParam("userId") long userId){
        Map<String,String> rtn = new HashMap<>();
        if(wishListService.delete(userId,wishListId)){
            rtn.put("message",new BaseException("RA-01-02").getErrorMessage().getMessage());
        }else {
            rtn.put("message",new BaseException("RA-00-03").getErrorMessage().getMessage());
        }
        return rtn;
    }
}
