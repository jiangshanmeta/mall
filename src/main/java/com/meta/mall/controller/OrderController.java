package com.meta.mall.controller;

import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.model.request.CreateOrderReq;
import com.meta.mall.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ApiRestResponse<String> create(@RequestBody CreateOrderReq createOrderReq) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateOrderReq>> violations = validator.validate(createOrderReq);
        if (!violations.isEmpty()) {
            String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            return ApiRestResponse.error(20000, msg);
        }

        return ApiRestResponse.success(orderService.create(createOrderReq));
    }

}
