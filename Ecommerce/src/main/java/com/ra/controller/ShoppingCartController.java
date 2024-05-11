package com.ra.controller;

import com.ra.exception.BaseException;
import com.ra.model.dto.request.AddressRequest;
import com.ra.model.dto.request.ShoppingCartRequest;
import com.ra.model.dto.response.ShoppingCartResponse;
import com.ra.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1/user/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> get(@RequestParam("userId") long userId) {
        List<ShoppingCartResponse> cartEntities = cartService.getAllShoppingCartByUserId(userId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", cartEntities);
        return rtn;
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> post(@RequestBody ShoppingCartRequest request, @RequestParam("userId") long userId) {
        ShoppingCartResponse cartResponse = cartService.add(request, userId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", cartResponse);
        return rtn;
    }

    @PutMapping("/{cartItemId}")
    @ResponseBody
    public Map<String, Object> put(@PathVariable(value = "cartItemId") int cartId, @RequestParam("quantity") int quantity, @RequestParam("userId") long userId) {
        ShoppingCartResponse cartResponse = cartService.update(quantity, userId, cartId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", cartResponse);
        return rtn;
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "cartItemId") int cartId, @RequestParam("userId") long userId) {
        cartService.delete(userId, cartId);
        return ResponseEntity.ok(new BaseException("RA-01-02").getErrorMessage());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@RequestParam("userId") long userId) {
        try {
            cartService.delete(userId);
            return ResponseEntity.ok(new BaseException("RA-01-02").getErrorMessage());
        } catch (Exception exception) {
            return ResponseEntity.ok(new BaseException("RA-00-03").getErrorMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity checkOut(@RequestBody AddressRequest request, @RequestParam("userId") long userId) {
        return cartService.checkOut(request, userId);
    }
}
