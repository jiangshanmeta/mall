package com.meta.mall.repository;

import com.meta.mall.model.pojo.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends ListCrudRepository<Category, Integer>, PagingAndSortingRepository<Category, Integer> {
    boolean existsByName(String name);

    List<Category> findAllByName(String name);
}
