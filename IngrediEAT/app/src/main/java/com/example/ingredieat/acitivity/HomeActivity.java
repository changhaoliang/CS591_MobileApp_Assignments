package com.example.ingredieat.acitivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ingredieat.adapter.FavoriteAdapter;
import com.example.ingredieat.adapter.RecipeAdapter;
import com.example.ingredieat.base.Category;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.fragment.CartFragment;
import com.example.ingredieat.fragment.FavoriteFragment;
import com.example.ingredieat.fragment.IngredientsFragment;
import com.example.ingredieat.fragment.CategoryItemFragment;
import com.example.ingredieat.R;
import com.example.ingredieat.fragment.RecipeDetailFragment;
import com.example.ingredieat.fragment.RecipeFragment;
import com.example.ingredieat.fragment.UserFragment;
import com.example.ingredieat.setting.Setting;
import com.example.ingredieat.utils.HttpUtils;
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
        CartFragment.CartFragmentListner, RecipeFragment.RecipeFragmentListener, FavoriteFragment.FavoriteFragmentListener
        , RecipeDetailFragment.DetailFragmentListener {
    private BottomNavigationView menuView;

    private static final long mBackPressThreshold = 3500;
    private FragmentManager fragmentManager;

    private HashMap<String, List<Ingredient>> allIngredients;
    private List<Recipe> allRecipes;
    private HashSet<Recipe> favoriteRecipes;                        //for favorite
    private Category category;
    private long mLastBackPress;
    private boolean favoriteFlag;
    private HashMap<String, HashSet<String>> selectedTotalIngredients;

    private CategoryItemFragment categoryItemFragment;
    private IngredientsFragment ingredientsFragment;
    private UserFragment userFragment;
    private CartFragment cartFragment;
    private RecipeFragment recipeFragment;
    private FavoriteFragment favoriteFragment;
    private RecipeDetailFragment recipeDetailFragment;
    private ProgressBar progressBar;

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
        favoriteFragment = new FavoriteFragment();

        fragmentManager = getSupportFragmentManager();
        allIngredients = new HashMap<>();
        previousSelectedIngredients = new HashMap<>();
        progressBar = findViewById(R.id.progress_loader);

        // Get the data of all ingredients from the server side by sending a GET request.
        getAllIngredients();

        // Get the data of all favorite recipes of the current user from the server side by sending a POST request.
        favoriteRecipes = new HashSet<>();

        favoriteFragment.setRecipes(favoriteRecipes);
        getFavorite();
        categoryItemFragment.setAllIngredients(allIngredients);
        menuView.setOnNavigationItemSelectedListener(this);
    }

     /**
      * ButtomNavigation methods, one id -> one mene item -> jump to one fragment
      */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
            case R.id.favourite:
                if (Setting.currentMenu != R.id.favourite) {
                    Setting.currentMenu = R.id.favourite;
                    fragmentTransaction.replace(R.id.fragment_container, favoriteFragment);
                    break;
                } else
                    return true;
            case R.id.user:
                Log.d(TAG, String.valueOf(Setting.currentMenu));
                if (Setting.currentMenu != R.id.user) {
                    Setting.currentMenu = R.id.user;
                    fragmentTransaction.replace(R.id.fragment_container, userFragment);
                    break;
                } else
                    return true;
        }
        fragmentTransaction.commit();

        return true;
    }

    /**
     * This method is used to get the data of all ingredients from the server side
     * by sending a GET request.
     */
    private void getAllIngredients() {
        String requestUrl = "/home/ingredients";
        progressBarVisibility(View.VISIBLE);
        final Toast toast = Toast.makeText(getApplicationContext(), "Cannot connect to server",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        HttpUtils.getRequest(requestUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                progressBarVisibility(View.INVISIBLE);
                e.printStackTrace();
                toast.show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    progressBarVisibility(View.INVISIBLE);
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

        if(selectedTotalIngredients != null) {
            for (String category : selectedTotalIngredients.keySet()) {
                selectedIngredientsNames.addAll(selectedTotalIngredients.get(category));
            }
        }
        if(!selectedIngredientsNames.isEmpty()) {
            Collections.sort(selectedIngredientsNames);
            for(String ingredientsNames: selectedIngredientsNames) {
                stringBuilder.append(ingredientsNames).append(",+");
            }
            stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
            if(allRecipes != null) { // Remove the original data;
                recipeFragment.setRecipes(new ArrayList<Recipe>());
            }
        }else{
            return;
        }
        // Set up the parameters.
        params.put("googleId", Setting.googleId);
        params.put("selectedIngredients", stringBuilder.toString());

        progressBarVisibility(View.VISIBLE);
        final Toast toast = Toast.makeText(getApplicationContext(), "Cannot connect to server",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        HttpUtils.postRequest("/home/listRecipesByIngredientsNames", params, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                progressBarVisibility(View.INVISIBLE);
                toast.show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    progressBarVisibility(View.INVISIBLE);
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

    /**
     * This method is used to get the data of all favorite recipes of the current user from the server side
     * by sending a POST request.
     */
    private void getFavorite(){
       Map<String, String> params = new HashMap<>();
       params.put("googleId", Setting.googleId);
       final Toast toast = Toast.makeText(getApplicationContext(), "Cannot connect to server",Toast.LENGTH_LONG);
       toast.setGravity(Gravity.CENTER, 0, 0);
       HttpUtils.postRequest("/home/listFavoriteRecipesByGoogleId", params, new Callback() {
           @Override
           public void onFailure(@NotNull Call call, @NotNull IOException e) {

               toast.show();
           }

           @Override
           public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               if(response.isSuccessful()) {
                   progressBarVisibility(View.INVISIBLE);
                   ResponseBody body = response.body();
                   if(body != null) {
                       String data = body.string();
                       // Here we use Fastjson to parse json string.
                       List<Recipe> recipes = JSON.parseArray(data, Recipe.class);
                       favoriteRecipes.addAll(recipes);
                       favoriteFlag = true;
                   }
               }
           }
       });
    }

    /**
     * Set Ingredients Fragment if Search sth.
     * @param category: jump to which category fragment
     * @param ifAll: if is general search
     * @param searchList: search results
     */
    @Override
    public void setFragment(final Category category, boolean ifAll, HashMap<Category, HashSet<Ingredient>> searchList) {
        if (!ifAll) {
            this.category = category;
            if (fragmentManager.getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            this.ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setAllIngredients(allIngredients);
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            List<Ingredient> categoryIngredients = allIngredients.get(category.getCategoryValue());
            ingredientsFragment.setIngredients(categoryIngredients);
            ingredientsFragment.setCategory(category);
        }

        else if (ifAll) {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            this.ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setAllIngredients(allIngredients);
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            List<Ingredient> list = new ArrayList<>();
            for (Category key : searchList.keySet()) {
                for (Ingredient i : searchList.get(key)) {
                    list.add(i);
                }
            }
            ingredientsFragment.setIngredients(list);
            ingredientsFragment.setCategory(Category.ALL);
        }
    }

    @Override
    public void showDetails(Recipe recipe, RecipeAdapter recipeAdapter) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        recipeDetailFragment = new RecipeDetailFragment(recipe, recipeAdapter);
        fragmentTransaction.add(R.id.fragment_container, recipeDetailFragment);
        fragmentTransaction.show(recipeDetailFragment);
        fragmentTransaction.hide(recipeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void addLikes(Recipe recipe) {
        if (favoriteRecipes == null)
            favoriteRecipes = new HashSet<>();
        favoriteRecipes.add(recipe);
    }

    @Override
    public void removeLikes(Recipe recipe) {
        if (favoriteRecipes == null)
            favoriteRecipes = new HashSet<>();
        favoriteRecipes.remove(recipe);
    }

    @Override
    public void showFavoriteDetails(Recipe recipe, FavoriteAdapter favoriteAdapter) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        recipeDetailFragment = new RecipeDetailFragment(recipe, favoriteAdapter);
        fragmentTransaction.add(R.id.fragment_container, recipeDetailFragment);
        fragmentTransaction.show(recipeDetailFragment);
        fragmentTransaction.hide(favoriteFragment);
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
    public void updateTotalSelected(Category c) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
        fragmentTransaction.replace(R.id.fragment_container, categoryItemFragment);
        fragmentTransaction.commit();

        HashMap<String, HashSet<String>> oldIngredients = ingredientsFragment.getSelectedIngredients();

        if (c.equals(Category.ALL)) {
            for (String key : oldIngredients.keySet()) {
                categoryItemFragment.updateTotalIngredients(Category.getCategoryName(key), oldIngredients.get(key));
            }
        }
        else {
            categoryItemFragment.updateTotalIngredients(category, oldIngredients.get(category.getCategoryValue()));
        }
    }

    @Override
    public void updateSelected(HashMap<String, HashSet<String>> totalIngredients) {
        categoryItemFragment.setSelectedTotalIngredients(totalIngredients);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (!favoriteFlag) {
                while (!favoriteFlag) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
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
        if(newSelectedIngredients != null){
            for(String key: newSelectedIngredients.keySet()){
                HashSet<String> hashSet1 = newSelectedIngredients.get(key);
                HashSet<String> hashSet2 = new HashSet<>(hashSet1);
                previousSelectedIngredients.put(key, hashSet2);
            }
        }
    }

    /**
     * check if select ingredients changed,
     * @return False -> do not refresh Recipe Fragment, True -> refresh Recipe Fragment
     */
    public boolean checkIfIngChanged() {
        HashMap<String, HashSet<String>> newSelectedIngredients = categoryItemFragment.getSelectedTotalIngredients();

        for(String key : previousSelectedIngredients.keySet()) {
            HashSet<String> category = previousSelectedIngredients.get(key);
            for (String ing : category) {
                System.out.println(ing);
            }
        }
        if( newSelectedIngredients!= null ) {
            for (String key : newSelectedIngredients.keySet()) {
                HashSet<String> category = newSelectedIngredients.get(key);
                for (String ing : category) {
                    System.out.println(ing);
                }
            }
        }
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

    private void progressBarVisibility(final int visibility) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(visibility);
            }
        });
    }

    @Override
    public void refresh() {
        getAllRecipes();
    }
}
