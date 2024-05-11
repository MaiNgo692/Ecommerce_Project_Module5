package com.ra.controller;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.dto.response.ProductResponse;
import com.ra.service.FileService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api.myservice.com/v1")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;

    @Autowired
    private CommonFunction<ProductResponse> commonFunction;

    @GetMapping("/products/search")
    @ResponseBody
    public Map<String, Object> get(@RequestParam(value = "searchName", required = false) String key) {
        List<ProductResponse> responses = productService.findByKey(key);
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses);
        return rtn;
    }

    @GetMapping("/products")
    @ResponseBody
    public Map<String, Object> get(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                   @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
                                   @RequestParam(name = "sort_by") String sortField,
                                   @RequestParam(name = "how_sort") String sortType) {
        Page<ProductResponse> responses = productService.findAllProductSellPageAndSort(sortField, sortType, pageNumber, pageSize);
        return commonFunction.mapResponse(responses, pageNumber, pageSize, sortField, sortType);
    }
    @GetMapping("/products/featured-products")
    @ResponseBody
    public Map<String, Object> getFeaturedProducts() {
        List<ProductResponse> responses = productService.getFeaturedProducts();
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses.subList(0,3));
        return rtn;
    }
    @GetMapping("/products/best-seller-products")
    @ResponseBody
    public Map<String, Object> getBestSellerProducts() {
        List<ProductResponse> responses = productService.getBestSellerProducts();
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses.subList(0,3));
        return rtn;
    }
    @GetMapping("/admin/dash-board/sales/best-seller-products")
    @ResponseBody
    public Map<String, Object> getBestSellerProductsInMonth() {
        List<ProductResponse> responses = productService.getBestSellerProductsInMonth();
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses.subList(0,3));
        return rtn;
    }
    @GetMapping("/products/new-products")
    @ResponseBody
    public Map<String, Object> getNewProduct() {
        List<ProductResponse> responses = productService.getNewProducts();
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses);
        return rtn;
    }

    @GetMapping("/products/categories/{categoryId}")
    @ResponseBody
    public Map<String, Object> getByCategory(@PathVariable("categoryId") long categoryId) {
        List<ProductResponse> responses = productService.findByCategoryId(categoryId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses);
        return rtn;
    }
    @GetMapping("/products/{productId}")
    @ResponseBody
    public Map<String, Object> get(@PathVariable("productId") long productId) {
        ProductResponse responses = productService.findByIdProductSell(productId);
        Map<String, Object> rtn = new LinkedHashMap<>();
        rtn.put("data", responses);
        return rtn;
    }

    @PostMapping("/admin/products")
    @ResponseBody
    public Map<String, Object> add(@RequestParam("image") MultipartFile image, @RequestParam("productName") String productName,
                                   @RequestParam("description") String description, @RequestParam("unitPrice") BigDecimal unitPrice,
                                   @RequestParam("stock") int stock, @RequestParam("categoryId") Long categoryId) throws IOException {
        Map<String, Object> rtn = new LinkedHashMap<>();
        ProductRequest productRequest = pasteToProductRequest(image,productName,description,unitPrice,stock,categoryId);
        ProductResponse productResponse = productService.add(productRequest);
        rtn.put("data", productResponse);
        return rtn;
    }

    @RequestMapping(value = "/admin/products", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAllProduct(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                             @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
                                             @RequestParam(name = "sort_by") String sortField,
                                             @RequestParam(name = "how_sort") String sortType) {
        Page<ProductResponse> responses = productService.findAllProductPageAndSort(sortField, sortType, pageNumber, pageSize);
        return commonFunction.mapResponse(responses, pageNumber, pageSize, sortField, sortType);
    }

    @GetMapping("admin/products/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") long productId) {
        ProductResponse responses = productService.findById(productId);
        return new ResponseEntity(responses, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    @ResponseBody
    public Map<String, Object> put(@PathVariable("productId") long productId,
                                   @RequestParam("image") MultipartFile image, @RequestParam("productName") String productName,
                                   @RequestParam("description") String description, @RequestParam("unitPrice") BigDecimal unitPrice,
                                   @RequestParam("stock") int stock, @RequestParam("categoryId") Long categoryId) throws IOException {

        Map<String, Object> rtn = new LinkedHashMap<>();
        ProductRequest productRequest = pasteToProductRequest(image,productName,description,unitPrice,stock,categoryId);
        ProductResponse productResponse = productService.put(productId, productRequest);
        rtn.put("data", productResponse);
        return rtn;
    }

    @DeleteMapping("/admin/products/{productId}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("productId") long productId) {
        return productService.delete(productId);
    }
    private ProductRequest pasteToProductRequest( MultipartFile image, String productName, String description, BigDecimal unitPrice, int stock, Long categoryId)throws IOException{
        fileService.save(image);
        Resource resource = fileService.load(image.getOriginalFilename());
        String imageUrl = String.valueOf(resource.getURL());
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName(productName);
        productRequest.setDescription(description);
        productRequest.setStock(stock);
        productRequest.setImage(imageUrl);
        productRequest.setUnitPrice(unitPrice);
        productRequest.setCategoryId(categoryId);
        return productRequest;
    }

}
