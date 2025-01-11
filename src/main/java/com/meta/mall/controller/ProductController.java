package com.meta.mall.controller;

import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.model.pojo.Product;
import com.meta.mall.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ApiRestResponse<Product> getProduct(@PathVariable Integer id) {
        return ApiRestResponse.success(productService.product(id));
    }

}
