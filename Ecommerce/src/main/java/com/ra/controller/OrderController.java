package com.ra.controller;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.SalesRequest;
import com.ra.model.dto.response.OrderDetailResponse;
import com.ra.model.dto.response.OrderResponse;
import com.ra.model.dto.response.SalesByCategoryResponse;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api.myservice.com/v1/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/user/history")
    @ResponseBody
    public Map<String, Object> getHistory(@RequestParam("userId") long userId) {
        List<OrderResponse> responseList = orderService.getOrderByUserId(userId);
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data", responseList);
        return rtn;
    }

    @GetMapping("/user/history/status/{orderStatus}")
    @ResponseBody
    public Map<String, Object> getOderByStatus(@PathVariable("orderStatus") String orderStatus, @RequestParam("userId") long userId) {
        List<OrderResponse> responseList = orderService.getOrderByUserIdAndStatus(userId, orderStatus);
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data", responseList);
        return rtn;
    }

    @PutMapping("/user/history/{serialNumber}/cancel")
    public Map<String, Object> cancelOrder(@PathVariable("serialNumber") String serialNumber, @RequestParam("userId") long userId) {
        Map<String, Object> rtn = new HashMap<>();
        if (orderService.cancelOrder(userId, serialNumber)) {
            rtn.put("message", new BaseException("RA-01-04").getErrorMessage().getMessage());
        } else {
            rtn.put("message", new BaseException("RA-00-03").getErrorMessage().getMessage());
        }
        return rtn;
    }

    @GetMapping("admin/orders")
    @ResponseBody
    public Map<String, Object> getAllOrder() {
        List<OrderResponse> responseList = orderService.getAllOrder();
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data", responseList);
        return rtn;
    }

    @GetMapping("/admin/orders/{orderStatus}")
    @ResponseBody
    public Map<String, Object> getAllOderByStatus(@PathVariable("orderStatus") String orderStatus) {
        List<OrderResponse> responseList = orderService.getAllOrderByStatus(orderStatus);
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data", responseList);
        return rtn;
    }
    @GetMapping("/admin/orders/{orderId}/oderStatus/{oderStatus}")
    @ResponseBody
    public Map<String, Object> changeOrderStatus(@PathVariable("orderId") long orderId,@PathVariable("oderStatus") String orderStatus){
        OrderResponse response = orderService.changeOrderStatus(orderId,orderStatus);
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data",response);
        return rtn;
    }

    @GetMapping("admin/dash-board/sales")
    @ResponseBody
    public Map<String, Object> sales(@RequestBody SalesRequest request) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        BigDecimal sales = orderService.getSalesPeriodTime(formatter.parse(request.getFrom()),formatter.parse(request.getTo()));
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("Sales from " +request.getFrom() +" to " + request.getTo(),sales);
        return rtn;
    }
    @GetMapping("admin/dash-board/sales/categories")
    @ResponseBody
    public Map<String, Object> salesByCategory(@RequestBody SalesRequest request) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<SalesByCategoryResponse> sales = orderService.getSalesByCategory(formatter.parse(request.getFrom()),formatter.parse(request.getTo()));
        Map<String, Object> rtn = new HashMap<>();
        rtn.put("data",sales);
        return rtn;
    }

}
