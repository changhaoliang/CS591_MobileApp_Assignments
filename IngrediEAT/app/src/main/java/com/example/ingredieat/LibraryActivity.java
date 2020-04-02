package com.example.ingredieat;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ingredieat.backend.Ingredient;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class LibraryActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private Context context;
    private ListView listView;
    private ListAdapter listAdapter;
    private Button meat_btn;
    private Button vege_btn;
    private Button other_btn;
    private ArrayList<Item> currentItems;
    private HashMap<Category, ArrayList<Item>> ingredients;
    private BottomNavigationView menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ingredients = new HashMap<>();
        ingredients.put(Category.MEAT, new ArrayList<Item>());
        ingredients.put(Category.VEGETABLE, new ArrayList<Item>());
        ingredients.put(Category.OTHER, new ArrayList<Item>());

        meat_btn = (Button)findViewById(R.id.meat_btn);
        vege_btn = (Button)findViewById(R.id.vege_btn);
        other_btn = (Button)findViewById(R.id.other_btn);
        linearLayout = (LinearLayout)findViewById(R.id.linear_layout);
        listView = (ListView)findViewById(R.id.list_view);
        currentItems = ingredients.get(Category.MEAT);
        menuView = (BottomNavigationView)findViewById(R.id.bottom_menu);

//        menuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Intent intent;
//                switch (item.getItemId()) {
//                    case R.id.add:
//                        intent = new Intent(getApplicationContext(), AddActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.search:
//                        intent = new Intent(getApplicationContext(), SearchActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.user:
//                        intent = new Intent(getApplicationContext(), UserActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.cook:
//                        intent = new Intent(getApplicationContext(), CookActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.favourite:
//                        intent = new Intent(getApplicationContext(), FavouriteActivity.class);
//                        startActivity(intent);
//                        break;
//                }
//                return true;
//            }
//        });
        menuView.getMenu().getItem(2).setChecked(true);
        context = getApplicationContext();

        addItem("Pork Belly", getResources().getDrawable(R.drawable.pork, null), Category.MEAT);
        addItem("Beef", getResources().getDrawable(R.drawable.beef, null), Category.MEAT);
        System.out.println(currentItems.size());

        addItem("Beef", getResources().getDrawable(R.drawable.beef, null), Category.VEGETABLE);
        updateList();

        meat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItems = ingredients.get(Category.MEAT);
                updateList();
            }
        });

        vege_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItems = ingredients.get(Category.VEGETABLE);
                updateList();
            }
        });

        other_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItems = ingredients.get(Category.OTHER);
                updateList();
            }
        });

    }
    public void addItem(String name, Drawable picture, Category category) {
        Item newItem = new Item(name, picture, false, category);
        ArrayList<Item> arrayList = ingredients.get(category);
        arrayList.add(newItem);
    }

    public void updateList() {
        listAdapter = new ItemAdapter(LibraryActivity.this, R.layout.item, currentItems);
        listView.setAdapter(listAdapter);
    }

    public void jump() {

    }


}
