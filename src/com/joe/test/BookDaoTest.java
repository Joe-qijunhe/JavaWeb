package com.joe.test;

import com.joe.dao.BookDao;
import com.joe.dao.impl.BookDaoImpl;
import com.joe.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class BookDaoTest {
    private BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null, "JavaWeb", "Joe", new BigDecimal(99), 10, 1, null));

    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(6);
    }

    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(9, "JavaWeb", "Joe", new BigDecimal(120), 10, 1, null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(9));
    }

    @Test
    public void queryBooks() {
        for (Book book : bookDao.queryBooks()) {
            System.out.println(book);
        }
    }

    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageItems() {
        List<Book> books = bookDao.queryForPageItems(0, 4);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10, 50));
    }

    @Test
    public void queryForPageItemsByPrice() {
        for (Book book : bookDao.queryForPageItemsByPrice(0, 4, 10, 50)) {
            System.out.println(book);
        }
    }
}