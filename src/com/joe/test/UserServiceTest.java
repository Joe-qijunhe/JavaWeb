package com.joe.test;

import com.joe.pojo.User;
import com.joe.service.UserService;
import com.joe.service.impl.UserServiceImpl;
import org.junit.Test;


public class UserServiceTest {
    UserService userService = new UserServiceImpl();

    @Test
    public void registerUser() {
        userService.registerUser(new User(null, "alex", "666", "alex@123.com"));
    }

    @Test
    public void login() {
        System.out.println(userService.login(new User(null, "alex", "666", null)));
    }

    @Test
    public void existUsername() {
        System.out.println(userService.existUsername("1"));
    }
}