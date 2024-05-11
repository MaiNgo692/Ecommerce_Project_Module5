package com.ra.model.dto.response;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetailResponse {
    private String serialNumber;
    private BigDecimal totalPrice;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String status;
    private String createdAt;
    private String receivedAt;
    private List<ProductOfOrderDetailResponse> listItem;
}
