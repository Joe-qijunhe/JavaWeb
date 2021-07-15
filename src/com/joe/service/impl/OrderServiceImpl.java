package com.joe.service.impl;

import com.joe.dao.BookDao;
import com.joe.dao.OrderDao;
import com.joe.dao.OrderItemDao;
import com.joe.dao.impl.BookDaoImpl;
import com.joe.dao.impl.OrderDaoImpl;
import com.joe.dao.impl.OrderItemDapImpl;
import com.joe.pojo.*;
import com.joe.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDapImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        String orderId = System.currentTimeMillis() + "" + userId;
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);
        //保存订单
        orderDao.saveOrder(order);
        //遍历购物车中的每一个商品项，转换成订单项
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            //获取商品项
            CartItem cartItem = entry.getValue();
            //转换为订单项
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            Book book = bookDao.queryBookById(cartItem.getId());
            //更新库存和销量
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);
        }
        //清空购物车
        cart.clear();
        return orderId;
    }

    @Override
    public List<Order> showAllOrders() {
        return orderDao.queryOrders();
    }

    @Override
    public void sendOrder(String orderId) {
        orderDao.changeOrderStatus(orderId, 1);
    }

    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        return orderItemDao.queryOrderItemsByOrderId(orderId);
    }

    @Override
    public List<Order> showMyOrders(Integer userId) {
        return orderDao.queryOrdersByUserId(userId);
    }

    @Override
    public void receiveOrder(String orderId) {
        orderDao.changeOrderStatus(orderId, 2);
    }

}
