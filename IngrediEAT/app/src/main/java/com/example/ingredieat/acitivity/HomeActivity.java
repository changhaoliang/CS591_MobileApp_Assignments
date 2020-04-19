package com.example.ingredieat.acitivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.FragmentUtils;
import com.example.ingredieat.base.Category;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.fragment.CartFragment;
import com.example.ingredieat.fragment.IngredientsFragment;
import com.example.ingredieat.fragment.CategoryItemFragment;
import com.example.ingredieat.R;
import com.example.ingredieat.fragment.RecipeDetailFragment;
import com.example.ingredieat.fragment.RecipeFragment;
import com.example.ingredieat.fragment.UserFragment;
import com.example.ingredieat.setting.Setting;
import com.example.ingredieat.utils.HttpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseActivity implements CategoryItemFragment.itemFragmentListener,
        IngredientsFragment.IngredientFragmentListener, BottomNavigationView.OnNavigationItemSelectedListener,
        CartFragment.CartFragmentListner, RecipeFragment.RecipeFragmentListener{
    private BottomNavigationView menuView;

    private static final long mBackPressThreshold = 3500;
    private FragmentManager fragmentManager;

    private Map<String, List<Ingredient>> allIngredients;
    private List<Recipe> allRecipes;
    private Category category;
    private long mLastBackPress;

    private CategoryItemFragment categoryItemFragment;
    private IngredientsFragment ingredientsFragment;
    private UserFragment userFragment;
    private CartFragment cartFragment;
    private RecipeFragment recipeFragment;
    private RecipeDetailFragment recipeDetailFragment;

    private HashMap<String, HashSet<String>> previousSelectedIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuView = findViewById(R.id.bottom_menu);

        categoryItemFragment = new CategoryItemFragment();
        userFragment = new UserFragment();
        cartFragment = new CartFragment();
        recipeFragment = new RecipeFragment();

        fragmentManager = getSupportFragmentManager();
        allIngredients = new HashMap<>();
        previousSelectedIngredients = new HashMap<>();

        // Get the data of all ingredients from the server side by sending a GET request.
        getAllIngredients();
        menuView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.hide(categoryItemFragment);
        switch (item.getItemId()) {
            case R.id.fridge:
                if (Setting.currentMenu != R.id.fridge) {
                    Setting.currentMenu = R.id.fridge;

                    fragmentTransaction.replace(R.id.fragment_container, categoryItemFragment);
                    break;
                } else
                    return true;
            case R.id.cart:
                if (Setting.currentMenu != R.id.cart) {
                    Setting.currentMenu = R.id.cart;
                    fragmentTransaction.replace(R.id.fragment_container, cartFragment);
                    HashMap<String, HashSet<String>> ingredients = categoryItemFragment.getSelectedTotalIngredients();
                    cartFragment.setTotalIngredients(ingredients);
                    break;
                } else
                    return true;
            case R.id.cook:
                if (Setting.currentMenu != R.id.cook) {
                    Setting.currentMenu = R.id.cook;

                    if (checkIfIngChanged()) {
                        getAllRecipes();
                    }
                    fragmentTransaction.replace(R.id.fragment_container, recipeFragment);
                    break;
                } else
                    return true;
            case R.id.user:
                if (Setting.currentMenu != R.id.user) {
                    Setting.currentMenu = R.id.user;
                    fragmentTransaction.replace(R.id.fragment_container, userFragment);
                    break;
                } else
                    return true;
        }
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//
////                if (item.getItemId() == R.id.cook){
////                    recipeFragment.setRecipes(searchRecipes());
////                }

        return true;
    }

    /**
     * This method is used to get the data of all ingredients from the server side
     * by sending a GET request.
     */
    private void getAllIngredients() {
        String requestUrl = "/home/ingredients";
        HttpUtils.getRequest(requestUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure -- >" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if(body != null) {
                        String data = body.string();
                        // Log.d(TAG, data);
                        // Here we use Fastjson to parse json string
                        List<Ingredient> ingredients = JSON.parseArray(data, Ingredient.class);
                        for(Ingredient ingredient: ingredients) {
                            String category = ingredient.getCategory();
                            if(allIngredients.containsKey(category)) {
                                allIngredients.get(category).add(ingredient);
                            }else{
                                List<Ingredient> categoryIngredients = new ArrayList<>();
                                categoryIngredients.add(ingredient);
                                allIngredients.put(category, categoryIngredients);
                            }
                        }
                    }
                    Message msg = Message.obtain();
                    handler1.sendMessage(msg);
                }
            }
        });
    }

    /**
     * This method is used to get the data of recommended recipes from the server side
     * by passing the selected ingredients.
     */
    private void getAllRecipes(){
        // Get the selected ingredients of the current user.
        Map<String, String> params = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        List<String> selectedIngredientsNames = new ArrayList<>();
        Map<String, HashSet<String>> selectedTotalIngredients = categoryItemFragment.getSelectedTotalIngredients();

        for(String category: selectedTotalIngredients.keySet()) {
            selectedIngredientsNames.addAll(selectedTotalIngredients.get(category));
        }

        if(!selectedIngredientsNames.isEmpty()) {
            Collections.sort(selectedIngredientsNames);
            for(String ingredientsNames: selectedIngredientsNames) {
                stringBuilder.append(ingredientsNames).append(",+");
            }
            stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
        }else{
            return;
        }
        // Set up the parameters.
        params.put("googleId", Setting.googleId);
        params.put("selectedIngredients", stringBuilder.toString());

        System.out.println("post request");
        HttpUtils.postRequest("/home/listRecipesByIngredientsNames", params, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure -- >" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if(body != null) {
                        String data = body.string();
                        // Here we use Fastjson to parse json string.
                        allRecipes = JSON.parseArray(data, Recipe.class);
                        Message msg = Message.obtain();
                        handler2.sendMessage(msg);
                    }
                }
            }
        });
    }

    @Override
    public void setFragment(boolean flag, final Category category) {
        if (flag) {
            this.category = category;
            if (fragmentManager.getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            this.ingredientsFragment = new IngredientsFragment();
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            List<Ingredient> categoryIngredients = allIngredients.get(category.getCategoryValue());
            ingredientsFragment.setIngredients(categoryIngredients);
            ingredientsFragment.setCategory(category);
        }
    }


    @Override
    public void showDetails(Recipe recipe) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        recipeDetailFragment = new RecipeDetailFragment(recipe, true);
        fragmentTransaction.add(R.id.fragment_container, recipeDetailFragment);
        fragmentTransaction.show(recipeDetailFragment);
        fragmentTransaction.hide(recipeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public boolean getSelected(Category category, String ingredient) {
        return categoryItemFragment.checkSelect(category, ingredient);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();


        } else {
            long currentTime = System.currentTimeMillis();
            if (Math.abs(currentTime - mLastBackPress) > mBackPressThreshold) {
                mLastBackPress = currentTime;
            } else {
                finish();
            }
        }
    }

    @Override
    public void updateTotalSelected() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
        fragmentTransaction.replace(R.id.fragment_container, categoryItemFragment);
        fragmentTransaction.commit();

        HashSet<String> newIngredients = ingredientsFragment.getSelectedIngredients();
        categoryItemFragment.updateTotalIngredients(category, newIngredients);
    }

    @Override
    public void updateSelected(HashMap<String, HashSet<String>> totalIngredients) {
        categoryItemFragment.setSelectedTotalIngredients(totalIngredients);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, categoryItemFragment, "item fragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };


    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            recipeFragment.setRecipes(allRecipes);
        }
    };

    public void setPreviousSelectedIngredients(HashMap<String, HashSet<String>> newSelectedIngredients) {
        previousSelectedIngredients.clear();
        previousSelectedIngredients.putAll(newSelectedIngredients);
    }

    public boolean checkIfIngChanged() {
        boolean res = false;
        HashMap<String, HashSet<String>> newSelectedIngredients = categoryItemFragment.getSelectedTotalIngredients();

        if (previousSelectedIngredients.size() == 0) {
            setPreviousSelectedIngredients(newSelectedIngredients);
            return true;
        }

        if (newSelectedIngredients.keySet().size() != previousSelectedIngredients.keySet().size()) {
            setPreviousSelectedIngredients(newSelectedIngredients);
            return true;
        }

        for (String key : newSelectedIngredients.keySet()) {
            if (!previousSelectedIngredients.containsKey(key)) {

                setPreviousSelectedIngredients(newSelectedIngredients);
                return true;
            }
            else {
                if (previousSelectedIngredients.get(key).size() == newSelectedIngredients.get(key).size()) {
                    for (String s : newSelectedIngredients.get(key)) {
                        if (!previousSelectedIngredients.get(key).contains(s)) {

                            setPreviousSelectedIngredients(newSelectedIngredients);
                            return true;
                        }
                    }
                } else {
                    setPreviousSelectedIngredients(newSelectedIngredients);
                    return true;
                }
            }
        }
        return false;

    }
}
