package com.example.ingredieat.service.impl;

import com.example.ingredieat.dao.IngredientDao;
import com.example.ingredieat.service.HomeService;
import com.example.ingredieat.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    IngredientDao ingredientDao;

    @Override
    public List<Ingredient> listAllIngredients() {
        return ingredientDao.listAllIngredients();
    }
}
