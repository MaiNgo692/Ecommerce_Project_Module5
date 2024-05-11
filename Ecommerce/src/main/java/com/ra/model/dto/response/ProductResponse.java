package com.ra.model.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private long id;
    private String  productName;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
}
