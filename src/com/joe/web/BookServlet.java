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

public class BookServlet extends BaseServlet {
    BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //添加完永远跳到最后一页，有边界检查所以加一没事
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 0);
        pageNo++;
        //获取请求参数，封装成Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        //调用bookService.addBook()保存图书
        bookService.addBook(book);
        //跳到图书列表页面 /manager/bookServlet?action=page
        // req.getRequestDispatcher("/manager/bookServlet?action=page").forward(req, resp);
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求的参数id
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService.deleteBookById删除图书
        bookService.deleteBookById(id);
        //重定向图书列表管理页面
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数，封装成book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());
        //调用bookService.updateBook
        bookService.updateBook(book);
        //重定向
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数图书编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService.queryBookById查询图书
        Book book = bookService.queryBookById(id);
        //保存图书到request域
        req.setAttribute("book", book);
        //请求转发到book_edit.jsp
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req, resp);
    }
    /**
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //通过BookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //把全部图书保存到request域中
        req.setAttribute("books", books);
        //请求转发到/pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }
    **/
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数 pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //调用bookService.page(pageNo, pageSize)
        Page<Book> page = bookService.page(pageNo, pageSize);
        page.setUrl("manager/bookServlet?action=page");
        //保存page对象到request域
        req.setAttribute("page", page);
        //请求转发到page/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }
}

