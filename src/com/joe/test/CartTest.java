package com.joe.test;

import com.joe.pojo.Cart;
import com.joe.pojo.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

public class CartTest {

    @Test
    public void addItem() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "java", 1, new BigDecimal(100), new BigDecimal(100)));
        System.out.println(cart);
    }

    @Test
    public void deleteItem() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void updateCount() {
    }
}