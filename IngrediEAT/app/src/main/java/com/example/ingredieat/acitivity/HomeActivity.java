package com.example.ingredieat.acitivity;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
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
import com.alibaba.fastjson.JSONArray;
import com.example.ingredieat.base.Category;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.fragment.CartFragment;
import com.example.ingredieat.fragment.IngredientsFragment;
import com.example.ingredieat.fragment.CategoryItemFragment;
import com.example.ingredieat.R;
import com.example.ingredieat.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HomeActivity extends BaseActivity implements CategoryItemFragment.itemFragmentListener, IngredientsFragment.IngredientFragmentListener {
    private BottomNavigationView menuView;
    private CategoryItemFragment categoryItemFragment;
    private static final long mBackPressThreshold = 3500;
    private FragmentManager fragmentManager;
    private IngredientsFragment ingredientsFragment;
    private List<Ingredient> allIngredients;
    private Category category;
    private long mLastBackPress;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        menuView = findViewById(R.id.bottom_menu);

        categoryItemFragment = new CategoryItemFragment();

        fragmentManager = getSupportFragmentManager();

        // Get the data of all ingredients from the server side by sending a GET request.
        getAllIngredients();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, categoryItemFragment, "item fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setTitle("Pantry");
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
                        setTitle("Pantry");
                        break;
                    case R.id.user:
                        fragmentTransaction.replace(R.id.fragment_container, new UserFragment());
                        fragmentTransaction.addToBackStack(null);
                        setTitle("User Account");
                        break;
                    case R.id.cart:
                        fragmentTransaction.replace(R.id.fragment_container, new CartFragment());
                        fragmentTransaction.addToBackStack(null);
                        setTitle("My Ingredients");
                        break;
//                    case R.string.cancel:
//                        System.out.println(menuView.getChildCount());
//                        recoverMenu();
//                        fragmentTransaction.show(categoryItemFragment);
//                        break;
//                    case R.string.add:
//                        recoverMenu();
//                        fragmentTransaction.show(categoryItemFragment);
//                        menuView.getMenu().getItem(0).setChecked(true);
//                        HashSet<String> newIngredients = ingredientsFragment.getSelectedIngredients();
//                        categoryItemFragment.updateTotalIngredients(category, newIngredients);
//                        break;
                }
                fragmentTransaction.commit();

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
                        // Here we use Fastjson to parse json string
                        allIngredients = JSON.parseArray(data, Ingredient.class);
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
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.ingredientsFragment = new IngredientsFragment();
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

//<<<<<<< HEAD
//            ArrayList<Ingredient> ingredients = new ArrayList<>();
//            if (category.equals(Category.MILK_EGGS_OTHER_DAIRY)) {
//                ingredients.add(new Ingredient(123, "milk", Category.MILK_EGGS_OTHER_DAIRY.getCategoryValue()));
//                ingredients.add(new Ingredient(12343214, "butter", Category.MILK_EGGS_OTHER_DAIRY.getCategoryValue()));
//                ingredients.add(new Ingredient(343, "egg", Category.MILK_EGGS_OTHER_DAIRY.getCategoryValue()));
//            }
//            ingredientsFragment.setIngredients(ingredients);
//
//=======

            // 此处替换成从后端存好的数据根据类别获取对应的ingredients
            List<Ingredient> categoryIngredients = new ArrayList<>();
            for(Ingredient ingredient: allIngredients) {
                if(ingredient.getCategory().equals(category.getCategoryValue())) {
                    categoryIngredients.add(ingredient);
                }
            }

            ingredientsFragment.setIngredients(categoryIngredients);
//>>>>>>> 246f2d692bd1f39442f4e9f5626fa79be0b0a55b
            ingredientsFragment.setView(category);


            // 此处替换成从后端存好的数据根据类别获取对应的ingredients
//            ArrayList<Ingredient> ingredients = new ArrayList<>();

//            ingredientsFragment.setIngredients(ingredients);
//            ingredientsFragment.setView(category);

//            menuView.getMenu().removeItem(R.id.user);
//            menuView.getMenu().removeItem(R.id.cook);
//            menuView.getMenu().removeItem(R.id.favourite);
//            menuView.getMenu().removeItem(R.id.cart);
//            menuView.getMenu().removeItem(R.id.fridge);
//
//            menuView.getMenu().add(1, R.string.add, 1, R.string.add);
//            menuView.getMenu().add(1, R.string.cancel, 1, R.string.cancel);
//
//            menuView.getMenu().getItem(0).setIcon(R.drawable.ok);
//            menuView.getMenu().getItem(1).setIcon(R.drawable.cancel);


        }
    }

    public void recoverMenu() {
        menuView.getMenu().removeItem(R.string.cancel);
        menuView.getMenu().removeItem(R.string.add);
        System.out.println("recover");
        System.out.println(menuView.getMenu().size());
        System.out.println("i4147");

        menuView.getMenu().add(1, R.id.fridge, 1, R.string.fridge);
        menuView.getMenu().add(1, R.id.cart, 1, R.string.cart);
        menuView.getMenu().add(1, R.id.cook, 1, R.string.cook);
        menuView.getMenu().add(1, R.id.favourite, 1, R.string.favourite);
        menuView.getMenu().add(1, R.id.user, 1, R.string.user);

        menuView.getMenu().getItem(0).setIcon(R.drawable.fridge);
        menuView.getMenu().getItem(1).setIcon(R.drawable.cart);
        menuView.getMenu().getItem(2).setIcon(R.drawable.cooker);
        menuView.getMenu().getItem(3).setIcon(R.drawable.heart);
        menuView.getMenu().getItem(4).setIcon(R.drawable.user);

        menuView.getMenu().getItem(0).setChecked(true);

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
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
        fragmentTransaction.replace(R.id.fragment_container, categoryItemFragment);
        fragmentTransaction.commit();

        HashSet<String> newIngredients = ingredientsFragment.getSelectedIngredients();
        categoryItemFragment.updateTotalIngredients(category, newIngredients);
    }
}
