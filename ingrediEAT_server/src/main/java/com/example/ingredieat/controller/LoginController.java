package com.example.ingredieat.controller;

import com.example.ingredieat.entity.User;
import com.example.ingredieat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class is used to handle the http requests sent by the client side related with the login activity.
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * This method is used to find whether the data of the given user exists in the database,
     * if it is not in the database, insert it.
     * @param user
     */
    @PostMapping("/findOrAddUser")
    public void getOrInsertUser(@RequestBody User user) {

        loginService.getOrInsertUser(user);
    }
}
