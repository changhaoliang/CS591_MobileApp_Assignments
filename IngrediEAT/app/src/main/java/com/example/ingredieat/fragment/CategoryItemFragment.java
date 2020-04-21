package com.example.ingredieat.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ingredieat.base.Category;
import com.example.ingredieat.base.CategoryItem;
import com.example.ingredieat.R;
import com.example.ingredieat.adapter.CategoryItemAdapter;
import com.example.ingredieat.entity.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CategoryItemFragment extends Fragment implements CategoryItemAdapter.MyClickListener {
    private ListView listView;
    private ListAdapter listAdapter;
    private ArrayList<CategoryItem> categories;
    private HashMap<Category, ArrayList<Ingredient>> ingredients;
    private itemFragmentListener itemFragmentListener;
    private HashMap<String, HashSet<String>> selectedTotalIngredients;


    public interface itemFragmentListener {
        void setFragment(boolean flag, Category category);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_item, container, false);
        listView = (ListView) myView.findViewById(R.id.list_view);
        if (selectedTotalIngredients == null) selectedTotalIngredients = new HashMap<>();
        ingredients = new HashMap<>();

        initializeList();
        return myView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        itemFragmentListener = (itemFragmentListener) context;
    }

    public void addCategoryItem(Category category, Drawable picture) {
        CategoryItem newCategoryItem = new CategoryItem(category, picture);
        categories.add(newCategoryItem);
    }

    public void updateList() {
        listAdapter = new CategoryItemAdapter(getContext(), R.layout.item, categories, this);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void clickListener(View v, Category category) {
        itemFragmentListener.setFragment(true, category);
    }

    public HashMap<Category, ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }

    public ListAdapter getListAdapter() {
        return listAdapter;
    }

    public void initializeList() {
        ingredients.put(Category.MEAT, new ArrayList<Ingredient>());
        ingredients.put(Category.PRODUCE, new ArrayList<Ingredient>());
        ingredients.put(Category.SEAFOOD, new ArrayList<Ingredient>());
        ingredients.put(Category.MILK_EGGS_OTHER_DAIRY, new ArrayList<Ingredient>());
        ingredients.put(Category.BAKING, new ArrayList<Ingredient>());
        ingredients.put(Category.BEVERAGE, new ArrayList<Ingredient>());
        ingredients.put(Category.SPICES_AND_SEASONINGS, new ArrayList<Ingredient>());
        ingredients.put(Category.CONDIMENTS, new ArrayList<Ingredient>());
        ingredients.put(Category.OIL_VINEGAR_SALAD_DRESSING, new ArrayList<Ingredient>());
        ingredients.put(Category.OTHERS, new ArrayList<Ingredient>());

        categories = new ArrayList<>();
        addCategoryItem(Category.MEAT, getResources().getDrawable(R.drawable.meat, null));
        addCategoryItem(Category.PRODUCE, getResources().getDrawable(R.drawable.vegetables, null));
        addCategoryItem(Category.SEAFOOD, getResources().getDrawable(R.drawable.seafood, null));
        addCategoryItem(Category.MILK_EGGS_OTHER_DAIRY, getResources().getDrawable(R.drawable.dairy2, null));
        addCategoryItem(Category.BAKING, getResources().getDrawable(R.drawable.baking, null));
        addCategoryItem(Category.BEVERAGE, getResources().getDrawable(R.drawable.drinking, null));
        addCategoryItem(Category.SPICES_AND_SEASONINGS, getResources().getDrawable(R.drawable.spice, null));
        addCategoryItem(Category.CONDIMENTS, getResources().getDrawable(R.drawable.ketchup, null));
        addCategoryItem(Category.OIL_VINEGAR_SALAD_DRESSING, getResources().getDrawable(R.drawable.oil, null));
        addCategoryItem(Category.OTHERS, getResources().getDrawable(R.drawable.others, null));



        listAdapter = new CategoryItemAdapter(getContext(), R.layout.item, categories, this);
        listView.setAdapter(listAdapter);
    }

    public void updateTotalIngredients(Category category, HashSet<String> newIngredients) {
        if (!selectedTotalIngredients.keySet().contains(category.getCategoryValue())) {
            selectedTotalIngredients.put(category.getCategoryValue(), new HashSet<String>());
        }
        selectedTotalIngredients.put(category.getCategoryValue(), newIngredients);
        System.out.println(selectedTotalIngredients.get(category.getCategoryValue()).size() + "total");
    }

    public boolean checkSelect(Category category, String ingredient) {
        if (selectedTotalIngredients.keySet().contains(category.getCategoryValue())) {
            return selectedTotalIngredients.get(category.getCategoryValue()).contains(ingredient);
        }

        return false;
    }

    public HashMap<String, HashSet<String>> getSelectedTotalIngredients() {
        return selectedTotalIngredients;
    }

    public void setSelectedTotalIngredients(HashMap<String, HashSet<String>> selectedTotalIngredients) {
        this.selectedTotalIngredients = selectedTotalIngredients;
    }
}
