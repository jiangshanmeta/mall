package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.model.pojo.Cart;
import com.meta.mall.model.pojo.Product;
import com.meta.mall.model.response.CartVO;
import com.meta.mall.repository.CartRepository;
import com.meta.mall.repository.ProductRepository;
import com.meta.mall.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
        }
        Product product = productOptional.get();
        if (!product.getStatus().equals(1)) {
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_SALE);
        }
        if (count > product.getStock()) {
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_ENOUGH);
        }
        Optional<Cart> cartOptional = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cartOptional.isEmpty()) {
            Cart cart = new Cart();
            cart.setQuantity(count);
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setSelected(1);

            cartRepository.save(cart);
        } else {
            Cart cart = cartOptional.get();
            cart.setQuantity(cart.getQuantity() + count);
            cartRepository.save(cart);
        }


        return list(userId);
    }

    @Override
    public List<CartVO> list(Integer userId) {
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        List<Integer> productIds = cartList.stream().map(Cart::getProductId).toList();
        List<Product> productList = productRepository.findAllById(productIds);
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        return cartList.stream().map((cart -> {
            CartVO cartVO = new CartVO();
            cartVO.setId(cart.getId());
            cartVO.setQuantity(cart.getQuantity());
            cartVO.setPrice(cart.getQuantity());
            cartVO.setSelected(cart.getSelected());
            cartVO.setProductId(cart.getProductId());

            cartVO.setUserId(userId);

            Product product = productMap.get(cart.getProductId());
            cartVO.setProductName(product.getName());
            cartVO.setProductImage(product.getImage());
            cartVO.setPrice(product.getPrice());
            cartVO.setTotalPrice(product.getPrice() * cart.getQuantity());
            return cartVO;
        })).toList();

    }

    @Override
    public List<CartVO> updateCount(Integer id, Integer userId, Integer count) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty() || !cartOptional.get().getUserId().equals(userId)) {
            throw new MallException(MallExceptionEnum.CART_NOT_EXIST);
        }


        Cart cart = cartOptional.get();
        cart.setQuantity(count);

        cartRepository.save(cart);

        return list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty() || !cartOptional.get().getUserId().equals(userId)) {
            throw new MallException(MallExceptionEnum.CART_NOT_EXIST);
        }

        cartRepository.deleteById(cartId);

        return list(userId);
    }


}
