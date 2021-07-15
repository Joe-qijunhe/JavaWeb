package com.joe.web;

import com.joe.pojo.Cart;
import com.joe.pojo.Order;
import com.joe.pojo.OrderItem;
import com.joe.pojo.User;
import com.joe.service.OrderService;
import com.joe.service.impl.OrderServiceImpl;
import com.joe.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    //生成订单
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取购物车对象，userid
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            return;
        }
        int userId = user.getId();

        //生成订单
        String orderId = null;
        orderId = orderService.createOrder(cart, userId);
        req.getSession().setAttribute("orderId", orderId);
        //请求转发
//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req, resp);
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }

    //查询所有订单（管理员）
    protected void showAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderService.showAllOrders();
        //保存所有orders到request域
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(req, resp);
    }

    //发货（管理员）
    protected void sendOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        orderService.sendOrder(req.getParameter("orderId"));
        //跳转回订单管理
        resp.sendRedirect(req.getHeader("Referer"));
    }

    //查看订单详情（管理员/用户）
    protected void showOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<OrderItem> orderItems = orderService.showOrderDetail(req.getParameter("orderId"));
        //保存订单号对应的所有orderItems到request域
        req.setAttribute("orderItems", orderItems);
        req.getRequestDispatcher("/pages/order/order_items.jsp").forward(req, resp);
    }

    //查看我的订单（用户）
    protected void showMyOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = WebUtils.parseInt(req.getParameter("userId"), 0);
        List<Order> orders = orderService.showMyOrders(userId);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);
    }

    //签收订单（用户）
    protected void receiveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        orderService.receiveOrder(req.getParameter("orderId"));
        //跳转回我的订单
        resp.sendRedirect(req.getHeader("Referer"));
    }
}
