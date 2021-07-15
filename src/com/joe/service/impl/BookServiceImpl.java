package com.joe.service.impl;

import com.joe.dao.BookDao;
import com.joe.dao.impl.BookDaoImpl;
import com.joe.pojo.Book;
import com.joe.pojo.Page;
import com.joe.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page = new Page<>();
        page.setPageSize(pageSize);

        //总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);

        //总页码数
        Integer pageTotal = pageTotalCount / page.getPageSize();
        if (pageTotalCount % page.getPageSize() > 0) {
            pageTotal++;
        }
        page.setPageTotal(pageTotal);

        page.setPageNo(pageNo);

        //当前页数据
        int begin = (page.getPageNo() - 1) * page.getPageSize();
        List<Book> items = bookDao.queryForPageItems(begin, page.getPageSize());
        page.setItems(items);
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<>();
        page.setPageSize(pageSize);

        //总记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min, max);
        page.setPageTotalCount(pageTotalCount);

        //总页码数
        Integer pageTotal = pageTotalCount / page.getPageSize();
        if (pageTotalCount % page.getPageSize() > 0) {
            pageTotal++;
        }
        page.setPageTotal(pageTotal);

        page.setPageNo(pageNo);

        //当前页数据
        int begin = (page.getPageNo() - 1) * page.getPageSize();
        List<Book> items = bookDao.queryForPageItemsByPrice(begin, page.getPageSize(), min, max);
        page.setItems(items);
        return page;
    }
}
