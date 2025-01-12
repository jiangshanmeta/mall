package com.meta.mall.service;

import com.meta.mall.model.request.CreateOrderReq;

public interface OrderService {
    String create(CreateOrderReq createOrderReq);
}
