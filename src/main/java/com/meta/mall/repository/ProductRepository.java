package com.meta.mall.repository;

import com.meta.mall.model.pojo.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends ListCrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
    boolean existsByName(String name);

}
