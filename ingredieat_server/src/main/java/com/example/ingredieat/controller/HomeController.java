package com.example.ingredieat.controller;


import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/ingredients")
    public List<Ingredient> listAllIngredients() {

        return homeService.listAllIngredients();
    }
}
