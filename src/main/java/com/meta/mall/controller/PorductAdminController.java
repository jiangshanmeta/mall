package com.meta.mall.controller;

import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.model.request.AddProductReq;
import com.meta.mall.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product/admin")
public class PorductAdminController {
    private final ProductService productService;

    public PorductAdminController(ProductService productService) {
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

}
