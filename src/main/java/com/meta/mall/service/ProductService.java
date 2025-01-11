package com.meta.mall.service;

import com.meta.mall.model.pojo.Product;
import com.meta.mall.model.request.AddProductReq;
import com.meta.mall.model.request.CategoryBatchUpdateReq;
import com.meta.mall.model.request.UpdateProductReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    void add(AddProductReq addProductReq);

    void update(UpdateProductReq updateProductReq);

    void delete(Integer id);

    void batchUpdateStatus(CategoryBatchUpdateReq categoryBatchUpdateReq);

    Page<Product> adminList(Pageable pageable);

    Product product(Integer id);
}
