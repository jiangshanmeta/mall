package com.meta.mall.repository;

import com.meta.mall.model.pojo.OrderItem;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderItemRepository extends ListCrudRepository<OrderItem, Integer> {
}
