package com.joe.web;

import com.joe.pojo.Book;
import com.joe.pojo.Page;
import com.joe.service.BookService;
import com.joe.service.impl.BookServiceImpl;
import com.joe.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientBookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //调用bookService.page(pageNo, pageSize)
        Page<Book> page = bookService.page(pageNo, pageSize);
        page.setUrl("client/bookServlet?action=page");
        //保存page对象到request域
        req.setAttribute("page", page);
        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }

    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"), 0);
        int max = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);

        Page<Book> page = bookService.pageByPrice(pageNo, pageSize, min, max);

        page.setUrl("client/bookServlet?action=pageByPrice&min=" + min + "&max=" + max);
        //保存page对象到request域
        req.setAttribute("page", page);
        //请求转发
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }
}
