package com.joe.test;

import com.joe.dao.OrderItemDao;
import com.joe.dao.impl.OrderItemDapImpl;
import com.joe.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderItemDaoTest {
    private OrderItemDao orderItemDao = new OrderItemDapImpl();

    @Test
    public void saveOrderItem() {
        orderItemDao.saveOrderItem(new OrderItem(null, "Java", 1, new BigDecimal(10), new BigDecimal(10), "123"));
    }

    @Test
    public void queryOrderItemsByOrderId() {
        System.out.println(orderItemDao.queryOrderItemsByOrderId("123"));
        System.out.println(orderItemDao.queryOrderItemsByOrderId("16261578225801"));
    }
}