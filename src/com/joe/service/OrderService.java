package com.joe.service;

import com.joe.pojo.Cart;
import com.joe.pojo.Order;
import com.joe.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
    //查询全部订单
    public List<Order> showAllOrders();
    //发货
    public void sendOrder(String orderId);
    //查看订单详情
    public List<OrderItem> showOrderDetail(String orderId);
    //查看我的订单
    public List<Order> showMyOrders(Integer userId);
    //确认收货
    public void receiveOrder(String orderId);

}
