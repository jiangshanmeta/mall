package com.meta.mall.controller;

import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.model.pojo.Product;
import com.meta.mall.model.request.AddProductReq;
import com.meta.mall.model.request.CategoryBatchUpdateReq;
import com.meta.mall.model.request.UpdateProductReq;
import com.meta.mall.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product/admin")
public class ProductAdminController {
    private final ProductService productService;

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ApiRestResponse<Void> addProduct(@RequestBody AddProductReq addProductReq) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<AddProductReq>> violations = validator.validate(addProductReq);
        if (!violations.isEmpty()) {
            String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            return ApiRestResponse.error(20000, msg);
        }

        productService.add(addProductReq);

        return ApiRestResponse.success();
    }

    @PostMapping("/update")
    public ApiRestResponse<Void> updateProduce(@RequestBody UpdateProductReq updateProductReq) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<UpdateProductReq>> violations = validator.validate(updateProductReq);
        if (!violations.isEmpty()) {
            String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            return ApiRestResponse.error(20000, msg);
        }


        productService.update(updateProductReq);

        return ApiRestResponse.success();
    }

    @DeleteMapping("/delete/{id}")
    public ApiRestResponse<Void> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ApiRestResponse.success();
    }


    @PostMapping("/batchUpdateStatus")
    public ApiRestResponse<Void> batchUpdateStatus(@RequestBody CategoryBatchUpdateReq categoryBatchUpdateReq) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CategoryBatchUpdateReq>> violations = validator.validate(categoryBatchUpdateReq);
        if (!violations.isEmpty()) {
            String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            return ApiRestResponse.error(20000, msg);
        }

        productService.batchUpdateStatus(categoryBatchUpdateReq);

        return ApiRestResponse.success();
    }

    @GetMapping("/list")
    public ApiRestResponse<Page<Product>> list(Pageable pageable) {
        return ApiRestResponse.success(productService.adminList(pageable));
    }

}
