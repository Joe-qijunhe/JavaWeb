package com.joe.service.impl;

import com.joe.dao.UserDao;
import com.joe.dao.impl.UserDaoImpl;
import com.joe.pojo.User;
import com.joe.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existUsername(String username) {
        if (userDao.queryUserByUsername(username) == null) {
            return false;
        } else {
            return true;
        }
    }
}
