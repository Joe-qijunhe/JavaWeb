package com.joe.dao;

import com.joe.pojo.OrderItem;

import java.util.List;

public interface OrderItemDao {
    //保存订单项
    public int saveOrderItem(OrderItem orderItem);
    //根据订单号查询订单明细
    public List<OrderItem> queryOrderItemsByOrderId(String orderId);
}
