package com.joe.test;

import com.joe.pojo.Cart;
import com.joe.pojo.CartItem;
import com.joe.service.OrderService;
import com.joe.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderServiceTest {
    private OrderService orderService = new OrderServiceImpl();
    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "java从入门到放弃", 1, new BigDecimal(100), new BigDecimal(100)));
        System.out.println(orderService.createOrder(cart, 1));
    }
}