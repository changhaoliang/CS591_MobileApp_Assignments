package com.example.ingredieat.acitivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ingredieat.base.Category;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.fragment.CartFragment;
import com.example.ingredieat.fragment.IngredientsFragment;
import com.example.ingredieat.fragment.CategoryItemFragment;
import com.example.ingredieat.R;
import com.example.ingredieat.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;

public class HomeActivity extends BaseActivity implements CategoryItemFragment.itemFragmentListener, IngredientsFragment.IngredientFragmentListener {
    private BottomNavigationView menuView;
    private CategoryItemFragment categoryItemFragment;
    private FragmentManager fragmentManager;
    private IngredientsFragment ingredientsFragment;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        menuView = findViewById(R.id.bottom_menu);

        categoryItemFragment = new CategoryItemFragment();

        fragmentManager = getSupportFragmentManager();

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
                    case R.string.cancel:
                        recoverMenu();
                        fragmentTransaction.show(categoryItemFragment);
                        menuView.getMenu().getItem(2).setChecked(true);
//                        itemFragment.cleanAllClick();
                        break;
                    case R.string.add:
                        recoverMenu();
                        fragmentTransaction.show(categoryItemFragment);
                        menuView.getMenu().getItem(2).setChecked(true);
                        HashSet<String> newIngredients = ingredientsFragment.getSelectedIngredients();
                        categoryItemFragment.updateTotalIngredients(category, newIngredients);
                        break;
                }
                fragmentTransaction.commit();

                return true;
            }
        });
    }

    @Override
    public void setFragment(boolean flag, Category category) {
        if (flag) {
            this.category = category;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.ingredientsFragment = new IngredientsFragment();
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


            // 此处替换成从后端存好的数据根据类别获取对应的ingredients
            ArrayList<Ingredient> ingredients = new ArrayList<>();

            ingredientsFragment.setIngredients(ingredients);
            ingredientsFragment.setView(category);

            menuView.getMenu().removeItem(R.id.user);
            menuView.getMenu().removeItem(R.id.cook);
            menuView.getMenu().removeItem(R.id.favourite);
            menuView.getMenu().removeItem(R.id.cart);
            menuView.getMenu().removeItem(R.id.fridge);

            menuView.getMenu().add(1, R.string.add, 1, R.string.add);
            menuView.getMenu().add(1, R.string.cancel, 1, R.string.cancel);

            menuView.getMenu().getItem(0).setIcon(R.drawable.ok);
            menuView.getMenu().getItem(1).setIcon(R.drawable.cancel);
        }
    }

    public void recoverMenu() {
        menuView.getMenu().removeItem(R.string.cancel);
        menuView.getMenu().removeItem(R.string.add);

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
    }

    @Override
    public boolean getSelected(Category category, String ingredient) {
        return categoryItemFragment.checkSelect(category, ingredient);
    }
}
