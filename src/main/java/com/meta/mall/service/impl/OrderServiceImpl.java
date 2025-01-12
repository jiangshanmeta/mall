package com.meta.mall.service.impl;

import com.meta.mall.exception.MallException;
import com.meta.mall.exception.MallExceptionEnum;
import com.meta.mall.filter.UserFilter;
import com.meta.mall.model.pojo.Order;
import com.meta.mall.model.pojo.OrderItem;
import com.meta.mall.model.pojo.Product;
import com.meta.mall.model.request.CreateOrderReq;
import com.meta.mall.model.response.CartVO;
import com.meta.mall.repository.OrderItemRepository;
import com.meta.mall.repository.OrderRepository;
import com.meta.mall.repository.ProductRepository;
import com.meta.mall.service.CartService;
import com.meta.mall.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(CartService cartService, ProductRepository productRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateOrderReq createOrderReq) {
        Integer userId = UserFilter.currentUser.getId();
        List<CartVO> cartVOList = cartService.list(userId);
        List<CartVO> selectedCartVOList = cartVOList.stream().filter((cartVO) -> cartVO.getSelected().equals(1)).toList();
        if (selectedCartVOList.isEmpty()) {
            throw new MallException(MallExceptionEnum.CART_SELECTED_EMPTY);
        }

        List<Product> productList = productRepository.findAllById(selectedCartVOList.stream().map(CartVO::getProductId).toList());
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        List<OrderItem> orderItemList = new ArrayList<>();
        int index = 0;

        for (CartVO cartVO : selectedCartVOList) {
            Product product = productMap.get(cartVO.getProductId());
            if (product == null) {
                throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
            }
            if (product.getStatus().equals(0)) {
                throw new MallException(MallExceptionEnum.PRODUCT_NOT_SALE);
            }
            if (product.getStock() < cartVO.getQuantity()) {
                throw new MallException(MallExceptionEnum.PRODUCT_NOT_ENOUGH);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getImage());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(product.getPrice() * cartVO.getQuantity());

            orderItemList.add(orderItem);
        }

        for (OrderItem orderItem : orderItemList) {
            Product product = productMap.get(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
        }
        productRepository.saveAll(productList);

        cartService.deleteSelectedCart(userId);

        int totalPrice = orderItemList.stream().mapToInt(OrderItem::getTotalPrice).sum();

        LocalDate now = LocalDate.now();

        String orderNo = userId +
                "-" +
                now.getYear() +
                "-" +
                now.getMonthValue() +
                "-" +
                now.getDayOfMonth() +
                "-" +
                totalPrice +
                "-" +
                new Random().nextInt(1, 10000);

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo(orderNo);
        order.setTotalPrice(totalPrice);
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setOrderStatus(1);

        orderItemList.forEach((orderItem -> orderItem.setOrderNo(orderNo)));
        orderItemRepository.saveAll(orderItemList);
        orderRepository.save(order);

        return orderNo;
    }


}
