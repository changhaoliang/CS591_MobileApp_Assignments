package com.example.ingredieat.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ingredieat.fragment.CartFragment;
import com.example.ingredieat.fragment.IngredientsFragment;
import com.example.ingredieat.fragment.ItemFragement;
import com.example.ingredieat.R;
import com.example.ingredieat.fragment.UserFragment;
import com.example.ingredieat.setting.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;

public class HomeActivity extends AppCompatActivity implements ItemFragement.ItemFragmentListner {
    private BottomNavigationView menuView;
    private ItemFragement itemFragement;
    private FragmentManager fragmentManager;
    private IngredientsFragment ingredientsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        menuView = (BottomNavigationView)findViewById(R.id.bottom_menu);


        itemFragement = new ItemFragement();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, itemFragement, "item fragment");
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
                fragmentTransaction.hide(itemFragement);
                switch (item.getItemId()) {
                    case R.id.fridge:
                        fragmentTransaction.show(itemFragement);
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
                        fragmentTransaction.show(itemFragement);
                        menuView.getMenu().getItem(2).setChecked(true);
//                        itemFragement.cleanAllClick();
                        break;
                    case R.string.add:
                        recoverMenu();
                        fragmentTransaction.show(itemFragement);
                        menuView.getMenu().getItem(2).setChecked(true);
                        HashSet<String> newIngredients = ingredientsFragment.getSelectedIngredients();
                        itemFragement.updateTotalIngredients(newIngredients);
                        break;
                }
                fragmentTransaction.commit();

                return true;
            }
        });
    }

    @Override
    public void setFragment(boolean flag, String category) {
        if (flag) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            this.ingredientsFragment = new IngredientsFragment();
            fragmentTransaction.replace(R.id.fragment_container, ingredientsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


            // 此处替换成从后端存好的数据根据类别获取对应的ingredients
            ArrayList<String> ingredients = new ArrayList<>();
            if (category.equals("Dairy")) {
                ingredients.add("milk");
                ingredients.add("butter");
                ingredients.add("egg");
                ingredients.add("cheddar");
                ingredients.add("sour cream");
                ingredients.add("cream cheese");
                ingredients.add("yogurt");
                ingredients.add("american cheese");
                ingredients.add("half and half");
                ingredients.add("feta");
            }
            ingredientsFragment.setIngredients(ingredients);

            setTitle(category);

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
        Setting.longClickFlag = false;
        Setting.shortClickFlag = false;
        Setting.count = 0;
    }
}
