package com.example.ingredieat.service.impl;


import com.example.ingredieat.dao.UserDao;
import com.example.ingredieat.entity.User;
import com.example.ingredieat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Override
    public void getOrInsertUser(User user) {
        if (userDao.getUserByGoogleId(user.getGoogleId()) == null) {
            userDao.insertUser(user);
        }
    }
}
