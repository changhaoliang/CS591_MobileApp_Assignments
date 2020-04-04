package com.example.ingredieat.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ingredieat.entity.Category;
import com.example.ingredieat.entity.Item;
import com.example.ingredieat.R;
import com.example.ingredieat.adapter.ItemAdapter;
import com.example.ingredieat.setting.Setting;

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
        Setting.count = 0;
        Setting.longClickFlag = false;
        Setting.shortClickFlag = false;

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
        addItem("Beef1", getResources().getDrawable(R.drawable.beef, null), Category.VEGETABLE);
        addItem("Beef2", getResources().getDrawable(R.drawable.beef, null), Category.MEAT);
        addItem("Beef3", getResources().getDrawable(R.drawable.beef, null), Category.MEAT);
        addItem("Beef4", getResources().getDrawable(R.drawable.beef, null), Category.VEGETABLE);
        addItem("Beef5", getResources().getDrawable(R.drawable.beef, null), Category.MEAT);
        addItem("Beef6", getResources().getDrawable(R.drawable.beef, null), Category.OTHER);

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

    public HashMap<Category, ArrayList<Item>> getIngredients() {
        return ingredients;
    }

    public ListAdapter getListAdapter() {
        return listAdapter;
    }

    public void cleanAllClick() {
        int size = listView.getChildCount();
        for(int i = 0; i < size; i++) {
            View view = listView.getChildAt(i);
            final CardView cardView = (CardView)view.findViewById(R.id.card_view);
            final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
            cardView.setBackgroundColor(Color.WHITE);
        }
        for (Category c : ingredients.keySet()) {
            for (Item item : ingredients.get(c)) {
                item.setSelected(false);
            }
        }
    }

    public void deleleIngredients() {
        for (Category c : ingredients.keySet()) {
            for (int i = ingredients.get(c).size() - 1; i > 0; i--) {
                if (ingredients.get(c).get(i).getSeclected()) {
                    ingredients.get(c).remove(i);
                }
            }
        }
        currentItems = ingredients.get(Category.MEAT);
        updateList();
        System.out.println(ingredients.get(Category.MEAT).size());
    }
}
