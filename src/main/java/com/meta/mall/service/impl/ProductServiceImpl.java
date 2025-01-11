package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.Product;
import com.meta.mall.model.request.AddProductReq;
import com.meta.mall.repository.ProductRepository;
import com.meta.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void add(AddProductReq addProductReq) {
        if (productRepository.existsByName(addProductReq.getName())) {
            throw new MallException(MallExceptionEnum.PRODUCT_NAME_DUPLICATED);
        }

        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);

        productRepository.save(product);
    }
}
