package com.example.ingredieat.acitivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.example.ingredieat.base.Category;
import com.example.ingredieat.base.Recipe;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.fragment.CartFragment;
import com.example.ingredieat.fragment.IngredientsFragment;
import com.example.ingredieat.fragment.CategoryItemFragment;
import com.example.ingredieat.R;
import com.example.ingredieat.fragment.RecipeDetailFragment;
import com.example.ingredieat.fragment.RecipeFragment;
import com.example.ingredieat.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseActivity implements CategoryItemFragment.itemFragmentListener,
        IngredientsFragment.IngredientFragmentListener,
        CartFragment.CartFragmentListner, RecipeFragment.RecipeFragmentListener,
        RecipeDetailFragment.RecipeDetailFragmentListener{
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        menuView = findViewById(R.id.bottom_menu);

        categoryItemFragment = new CategoryItemFragment();
        userFragment = new UserFragment();
        cartFragment = new CartFragment();
        recipeFragment = new RecipeFragment();

        fragmentManager = getSupportFragmentManager();
        allIngredients = new HashMap<>();
        allRecipes = new ArrayList<>();
        // Get the data of all ingredients from the server side by sending a GET request.
        getAllIngredients();
        getAllRecipes();
        recipeFragment.setRecipes(allRecipes);

        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (fragmentManager.getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(categoryItemFragment);
                switch (item.getItemId()) {
                    case R.id.fridge:
                        fragmentTransaction.show(categoryItemFragment);
                        break;
                    case R.id.user:
                        fragmentTransaction.replace(R.id.fragment_container, userFragment);
                        fragmentTransaction.addToBackStack(null);
                        break;
                    case R.id.cart:
                        fragmentTransaction.replace(R.id.fragment_container, cartFragment);
                        fragmentTransaction.addToBackStack(null);
                        HashMap<String, HashSet<String>> ingredients = categoryItemFragment.getSelectedTotalIngredients();
                        cartFragment.setTotalIngredients(ingredients);
                        break;
                    case R.id.cook:
                        fragmentTransaction.replace(R.id.fragment_container, recipeFragment);
                        fragmentTransaction.addToBackStack(null);
                        break;
                }
                fragmentTransaction.commit();

//                if (item.getItemId() == R.id.cook){
//                    recipeFragment.setRecipes(searchRecipes());
//                }

                return true;
            }
        });
    }

    /**
     * This method is used to get the data of all ingredients from the server side by sending a GET request.
     *
     * @return an ArrayList storing all the ingredient objects;
     */
    private void getAllIngredients() {
        String requestUrl = "/home/ingredients";
        getRequest(requestUrl, new Callback() {
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

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.fragment_container, categoryItemFragment, "item fragment");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });
    }

    private void getAllRecipes(){
        //test
        for(int i=0; i<10; i++) {
            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 12000, 4.5f));
            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 200, 3));
            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                    "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
            allRecipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
        }

        //getRequest(){
        // add to allRecipes;
        // }
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

            // 此处替换成从后端存好的数据根据类别获取对应的ingredients
            List<Ingredient> categoryIngredients = allIngredients.get(category.getCategoryValue());
            ingredientsFragment.setIngredients(categoryIngredients);
            ingredientsFragment.setCategory(category);
        }
    }


    @Override
    public void showDetails(Recipe recipe) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        recipeDetailFragment = new RecipeDetailFragment(recipe);
        fragmentTransaction.replace(R.id.fragment_container, recipeDetailFragment);
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
            getSupportFragmentManager().popBackStack();

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

    public List<Recipe> searchRecipes(){
        List<Recipe> recipes = new ArrayList<>();
        //test
        recipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 12000, 4.5f));
        recipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs", 200, 3));
        recipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
        recipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
        recipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
        recipes.add(new Recipe("https://spoonacular.com/recipeImages/716429-556x370.jpg",
                "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs"));
        recipes.add(new Recipe("https://spoonacular.com/recipeImages/73420-312x231.jpg", "baking powder"));
        return recipes;
    }

    @Override
    public void updateLikes() {
    }
}
