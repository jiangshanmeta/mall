package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.Product;
import com.meta.mall.model.request.AddProductReq;
import com.meta.mall.model.request.CategoryBatchUpdateReq;
import com.meta.mall.model.request.UpdateProductReq;
import com.meta.mall.repository.ProductRepository;
import com.meta.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void update(UpdateProductReq updateProductReq) {
        Optional<Product> optionalProduct = productRepository.findById(updateProductReq.getId());
        if (optionalProduct.isEmpty()) {
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
        }

        Product product = optionalProduct.get();
        if (updateProductReq.getName() != null) {
            product.setName(updateProductReq.getName());
        }
        if (updateProductReq.getDetail() != null) {
            product.setDetail(updateProductReq.getDetail());
        }
        if (updateProductReq.getImage() != null) {
            product.setImage(updateProductReq.getImage());
        }
        if (updateProductReq.getPrice() != null) {
            product.setPrice(updateProductReq.getPrice());
        }
        if (updateProductReq.getCategoryId() != null) {
            product.setCategoryId(updateProductReq.getCategoryId());
        }
        if (updateProductReq.getStock() != null) {
            product.setStock(updateProductReq.getStock());
        }
        if (updateProductReq.getStatus() != null) {
            product.setStatus(updateProductReq.getStatus());
        }

        productRepository.save(product);

    }

    @Override
    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
        }
        productRepository.deleteById(id);
    }

    @Override
    public void batchUpdateStatus(CategoryBatchUpdateReq categoryBatchUpdateReq) {
        List<Product> products = productRepository.findAllById(Arrays.asList(categoryBatchUpdateReq.getIds()));
        products.forEach((product) -> product.setStatus(categoryBatchUpdateReq.getStatus()));

        productRepository.saveAll(products);
    }

    @Override
    public Page<Product> adminList(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product product(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST));
    }


}
