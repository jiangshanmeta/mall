package com.meta.mall.controller;

import com.meta.mall.common.ApiRestResponse;
import com.meta.mall.filter.UserFilter;
import com.meta.mall.model.request.CartAddReq;
import com.meta.mall.model.response.CartVO;
import com.meta.mall.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ApiRestResponse<List<CartVO>> addCart(@RequestBody CartAddReq cartAddReq) {
        List<CartVO> list = cartService.add(UserFilter.currentUser.getId(), cartAddReq.getProductId(), cartAddReq.getCount());
        return ApiRestResponse.success(list);
    }

    @GetMapping("/list")
    public ApiRestResponse<List<CartVO>> list() {
        return ApiRestResponse.success(cartService.list(UserFilter.currentUser.getId()));
    }

    @PostMapping("/update/{id}")
    public ApiRestResponse<List<CartVO>> update(@PathVariable Integer id, @RequestParam Integer count) {
        return ApiRestResponse.success(cartService.updateCount(id, UserFilter.currentUser.getId(), count));
    }

    @DeleteMapping("/delete/{id}")
    public ApiRestResponse<List<CartVO>> delete(@PathVariable Integer id) {
        return ApiRestResponse.success(cartService.delete(UserFilter.currentUser.getId(), id));
    }


}
