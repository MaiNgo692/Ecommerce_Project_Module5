package com.ra.model.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String  productName;
    private String description;
    private BigDecimal unitPrice;
    private int stock;
    private String image;
    private long categoryId;
}
