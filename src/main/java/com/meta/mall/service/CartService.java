package com.meta.mall.service;

import com.meta.mall.model.response.CartVO;

import java.util.List;

public interface CartService {
    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> list(Integer userId);

    List<CartVO> updateCount(Integer id, Integer userId, Integer count);

    List<CartVO> delete(Integer userId, Integer cartId);

    void deleteSelectedCart(Integer userId);
}
