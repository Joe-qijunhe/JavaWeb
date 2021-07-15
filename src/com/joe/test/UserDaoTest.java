package com.joe.test;

import com.joe.dao.UserDao;
import com.joe.dao.impl.UserDaoImpl;
import com.joe.pojo.User;
import org.junit.Test;


public class UserDaoTest {
    UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {
        System.out.println(userDao.queryUserByUsername("admin"));
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        System.out.println(userDao.queryUserByUsernameAndPassword("admin", "123qwe"));
        System.out.println(userDao.queryUserByUsernameAndPassword("joe", "admain123"));
    }

    @Test
    public void saveUser() {
        System.out.println(userDao.saveUser(new User(null, "jojo", "123", "jojo@outlook.com")));

    }
}