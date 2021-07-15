package com.joe.test;

import com.joe.service.BookService;
import com.joe.service.impl.BookServiceImpl;
import org.junit.Test;

public class BookServiceTest {
    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
    }

    @Test
    public void deleteBookById() {
    }

    @Test
    public void updateBook() {
    }

    @Test
    public void queryBookById() {
    }

    @Test
    public void queryBooks() {
    }

    @Test
    public void page() {
        System.out.println(bookService.page(1, 4));
    }
}