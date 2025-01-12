package com.meta.mall.repository;

import com.meta.mall.model.pojo.Order;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Integer> {
}
