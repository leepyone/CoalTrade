package com.example.demo.user.service.imp;

import com.example.demo.user.DAO.UserDao;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class userImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User GetUser(String account) {
        return userDao.getUserByAccount(account);
    }
}
