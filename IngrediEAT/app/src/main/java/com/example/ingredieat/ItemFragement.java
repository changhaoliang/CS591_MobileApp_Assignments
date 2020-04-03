package com.example.ingredieat;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemFragement extends Fragment implements ItemAdapter.MyLongClickListner {
    private LinearLayout linearLayout;
    private Context context;
    private ListView listView;
    private ListAdapter listAdapter;
    private Button meat_btn;
    private Button vege_btn;
    private Button other_btn;
    private ArrayList<Item> currentItems;
    private HashMap<Category, ArrayList<Item>> ingredients;
    private ItemFragmentListner itemFragmentListner;

    public interface ItemFragmentListner {
        public void setMenu(boolean flag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_item, container, false);
        meat_btn = (Button)myView.findViewById(R.id.meat_btn);
        vege_btn = (Button)myView.findViewById(R.id.vege_btn);
        other_btn = (Button)myView.findViewById(R.id.other_btn);
        linearLayout = (LinearLayout)myView.findViewById(R.id.fragment_container);
        listView = (ListView)myView.findViewById(R.id.list_view);

        ingredients = new HashMap<>();
        ingredients.put(Category.MEAT, new ArrayList<Item>());
        ingredients.put(Category.VEGETABLE, new ArrayList<Item>());
        ingredients.put(Category.OTHER, new ArrayList<Item>());
        currentItems = ingredients.get(Category.MEAT);

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

        addItem("Pork Belly", getResources().getDrawable(R.drawable.pork, null), Category.MEAT);
        addItem("Beef", getResources().getDrawable(R.drawable.beef, null), Category.MEAT);
        addItem("Beef", getResources().getDrawable(R.drawable.beef, null), Category.VEGETABLE);
        updateList();

        return myView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        itemFragmentListner = (ItemFragmentListner) context;
    }

    public void addItem(String name, Drawable picture, Category category) {
        Item newItem = new Item(name, picture, false, category);
        ArrayList<Item> arrayList = ingredients.get(category);
        arrayList.add(newItem);
    }

    public void updateList() {
        listAdapter = new ItemAdapter(getContext(), R.layout.item, currentItems, this);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void longClickListner(View v) {
        itemFragmentListner.setMenu(true);
    }
}
