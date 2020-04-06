package com.example.ingredieat.acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ingredieat.fragment.ItemFragement;
import com.example.ingredieat.R;
import com.example.ingredieat.fragment.UserFragment;
import com.example.ingredieat.setting.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibraryActivity extends AppCompatActivity implements ItemFragement.ItemFragmentListner {
    private BottomNavigationView menuView;
    private ItemFragement itemFragement;
    private FragmentManager fragmentManager;

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

        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (fragmentManager.getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(itemFragement);
                switch (item.getItemId()) {
                    case R.id.fridge:
                        fragmentTransaction.show(itemFragement);
                        break;
                    case R.id.user:
                        fragmentTransaction.replace(R.id.fragment_container, new UserFragment());
                        fragmentTransaction.addToBackStack(null);
                        break;
                    case R.string.cancel:
                        recoverMenu();
                        fragmentTransaction.show(itemFragement);
                        menuView.getMenu().getItem(2).setChecked(true);
//                        itemFragement.cleanAllClick();
                        break;
                    case R.string.delete:
                        recoverMenu();
                        fragmentTransaction.show(itemFragement);
                        menuView.getMenu().getItem(2).setChecked(true);
//                        itemFragement.deleleIngredients();
                        break;
                }
                System.out.println(getSupportFragmentManager().getBackStackEntryCount());
                fragmentTransaction.commit();

                return true;
            }
        });
    }

    @Override
    public void setMenu(boolean flag) {
        if (flag) {
            menuView.getMenu().removeItem(R.id.user);
            menuView.getMenu().removeItem(R.id.cook);
            menuView.getMenu().removeItem(R.id.favourite);
            menuView.getMenu().removeItem(R.id.cart);
            menuView.getMenu().removeItem(R.id.fridge);

            menuView.getMenu().add(1, R.string.delete, 1, R.string.delete);
            menuView.getMenu().add(1, R.string.cancel, 1, R.string.cancel);

            System.out.println(menuView.getMenu().getItem(0).getItemId());

            menuView.getMenu().getItem(0).setIcon(R.drawable.delete);
            menuView.getMenu().getItem(1).setIcon(R.drawable.cancel);
        }
    }

    public void recoverMenu() {
        menuView.getMenu().removeItem(R.string.cancel);
        menuView.getMenu().removeItem(R.string.delete);

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
