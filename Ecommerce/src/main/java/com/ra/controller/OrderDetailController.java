package com.ra.controller;

import com.ra.model.dto.response.OrderDetailResponse;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1/")
public class OrderDetailController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/user/history/{serialNumber}")
    @ResponseBody
    public Map<String, Object> getOrderDetail(@PathVariable("serialNumber") String serialNumber, @RequestParam("userId") long userId){
        OrderDetailResponse response = orderService.getOderDetailBySerialNumber(serialNumber,userId);
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data",response);
        return rtn;
    }

    @GetMapping("/admin/orders/detail/{orderId}")
    @ResponseBody
    public Map<String, Object> getOderById(@PathVariable("orderId") long orderId){
        OrderDetailResponse response = orderService.getOderDetailByOrderId(orderId);
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data",response);
        return rtn;
    }

}
