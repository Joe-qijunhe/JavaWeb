package com.joe.test;

import com.joe.dao.OrderDao;
import com.joe.dao.impl.OrderDaoImpl;
import com.joe.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class OrderDaoTest {
    private OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void saveOrder() {
        orderDao.saveOrder(new Order("123", new Date(), new BigDecimal(100), 0, 1));
    }

    @Test
    public void queryOrders() {
        System.out.println(orderDao.queryOrders());
    }

    @Test
    public void changeOrderStatus() {
        orderDao.changeOrderStatus("123", 1);
    }

    @Test
    public void queryOrdersByUserId() {
        System.out.println(orderDao.queryOrdersByUserId(1));
    }
}