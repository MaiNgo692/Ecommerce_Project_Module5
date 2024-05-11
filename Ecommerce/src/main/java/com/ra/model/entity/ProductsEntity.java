package com.ra.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@jakarta.persistence.Table(name = "products", schema = "ecommerce", catalog = "")
public class ProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "product_id", nullable = false)
    private Long productId;
    @Basic
    @Column(name = "sku", nullable = true, length = 100,unique=true)
    private String sku;
    @Basic
    @Column(name = "product_name", nullable = false, length = 100,unique=true)
    private String productName;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;
    @Basic
    @Column(name = "stock_quantity", nullable = true)
    @Min(value = 0,message = "{value.min.error}")
    private Integer stockQuantity;
    @Basic
    @Column(name = "image", nullable = true, length = 255)
    private String image;
    @Basic
    @Column(name = "category_id", nullable = true)
    private Long categoryId;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Date createdAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Date updateAt;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "categoryId", referencedColumnName = "category_id", nullable = false)
//    private CategoryEntity categoryByCategoryId;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productsByProductId")
//    private List<ShopingCartEntity> shopingCartEntities;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productsByProductId")
//    private List<OrderDetailsEntity> orderDetailsEntities;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productsByProductId")
//    private List<WishListEntity> wishListEntities;

//    public ProductsEntity(long productId, String sku, String productName, String description, BigDecimal unitPrice, Integer stockQuantity, String image, Long categoryId, Date createdAt, Date updateAt) {
//        this.productId = productId;
//        this.sku = sku;
//        this.productName = productName;
//        this.description = description;
//        this.unitPrice = unitPrice;
//        this.stockQuantity = stockQuantity;
//        this.image = image;
//        this.categoryId = categoryId;
//        this.createdAt = createdAt;
//        this.updateAt = updateAt;
//        this.categoryByCategoryId= new CategoryEntity(categoryId);
//    }

    public ProductsEntity(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsEntity that = (ProductsEntity) o;
        return productId == that.productId && Objects.equals(sku, that.sku) && Objects.equals(productName, that.productName) && Objects.equals(description, that.description) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(stockQuantity, that.stockQuantity) && Objects.equals(image, that.image) && Objects.equals(categoryId, that.categoryId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updateAt, that.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sku, productName, description, unitPrice, stockQuantity, image, categoryId, createdAt, updateAt);
    }
}
