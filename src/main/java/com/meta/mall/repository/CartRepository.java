package com.meta.mall.repository;

import com.meta.mall.model.pojo.Cart;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends ListCrudRepository<Cart, Integer>, PagingAndSortingRepository<Cart, Integer> {
    Optional<Cart> findByUserIdAndProductId(Integer userId, Integer productId);

    List<Cart> findAllByUserId(Integer userId);

    void deleteByUserIdAndSelected(Integer userId, Integer selected);
}
