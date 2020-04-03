package com.example.ingredieat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibraryActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private BottomNavigationView menuView;
    private ItemFragement itemFragement;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        linearLayout = (LinearLayout) findViewById(R.id.fragment_container);
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
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                }


                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(itemFragement);
                switch (item.getItemId()) {
                    case R.id.cook:
                        fragmentTransaction.show(itemFragement);
                        break;
                    case R.id.user:
                        fragmentTransaction.replace(R.id.fragment_container, new UserFragment());
                        fragmentTransaction.addToBackStack(null);
                        break;
                }
                fragmentTransaction.commit();

                return true;
            }
        });
        menuView.getMenu().getItem(2).setChecked(true);

    }


}
