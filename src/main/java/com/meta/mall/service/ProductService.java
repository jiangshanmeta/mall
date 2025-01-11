package com.meta.mall.service;

import com.meta.mall.model.request.AddProductReq;
import com.meta.mall.model.request.UpdateProductReq;

public interface ProductService {
    void add(AddProductReq addProductReq);

    void update(UpdateProductReq updateProductReq);
}
