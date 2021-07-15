package com.joe.web;

import com.google.gson.Gson;
import com.joe.pojo.Book;
import com.joe.pojo.Cart;
import com.joe.pojo.CartItem;
import com.joe.service.BookService;
import com.joe.service.impl.BookServiceImpl;
import com.joe.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet{
    private BookService bookService = new BookServiceImpl();

//    //加入购物车
//    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //获取请求的参数
//        int id = WebUtils.parseInt(req.getParameter("id"), 0);
//        //调用bookService.queryBookById来得到图书信息
//        Book book = bookService.queryBookById(id);
//        //图书信息转换为CartItem项
//        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
//        //调用cart.addItem添加商品
//        //购物车保存在用户对应的session里
//        Cart cart = (Cart) req.getSession().getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            req.getSession().setAttribute("cart", cart);
//        }
//        cart.addItem(cartItem);
//        req.getSession().setAttribute("lastName", cartItem.getName());
//        //重定向回商品原来在的地址页面
//        resp.sendRedirect(req.getHeader("Referer"));
//    }

    //加入购物车
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService.queryBookById来得到图书信息
        Book book = bookService.queryBookById(id);
        //图书信息转换为CartItem项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用cart.addItem添加商品
        //购物车保存在用户对应的session里
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        req.getSession().setAttribute("lastName", cartItem.getName());

        Map<String, Object> resultMap = new HashMap<>();
        //商品总数
        resultMap.put("totalCount", cart.getTotalCount());
        //最近添加的商品的名称
        resultMap.put("lastName", cartItem.getName());

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);

    }

    //删除商品
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //删除了购物车商品
            cart.deleteItem(id);
            //重定向回商品原来在的地址页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    //清空购物车
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //清空购物车
            cart.clear();
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    //修改商品数
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        int count = WebUtils.parseInt(req.getParameter("count"), 1);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //修改商品数量
            cart.updateCount(id, count);
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
}
