package com.example.ingredieat.controller;

import com.example.ingredieat.entity.User;
import com.example.ingredieat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/findOrAddUser")
    public void findOrAddUser(@RequestBody User user) {

       loginService.findOrAddUser(user);
    }
}
