package com.meta.mall.service;

import com.meta.mall.model.response.CartVO;

import java.util.List;

public interface CartService {
    List<CartVO> add(Integer userId, Integer productId, Integer count);

    List<CartVO> list(Integer userId);
}
