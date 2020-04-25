package com.example.ingredieat.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ingredieat.base.Category;
import com.example.ingredieat.base.CategoryItem;
import com.example.ingredieat.R;
import com.example.ingredieat.adapter.CategoryItemAdapter;
import com.example.ingredieat.entity.Ingredient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CategoryItemFragment extends Fragment implements CategoryItemAdapter.MyClickListener {
    private ListView listView;
    private ListAdapter listAdapter;
    private ArrayList<CategoryItem> categories;
    private HashMap<Category, ArrayList<Ingredient>> ingredients;
    private itemFragmentListener itemFragmentListener;
    private HashMap<String, HashSet<String>> selectedTotalIngredients;
    private SearchView searchView;
    private HashMap<Category, HashSet<Ingredient>> searchList;

    private HashMap<String, List<Ingredient>> allIngredients;

    public interface itemFragmentListener {
        void setFragment(boolean flag, Category category, boolean ifAll, HashMap<Category, HashSet<Ingredient>> searchList);
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
        categories = new ArrayList<>();

        searchView = (SearchView) myView.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList = new HashMap<>();

                for (Category key : ingredients.keySet()) {
                    for (Ingredient i : ingredients.get(key)) {
                        if (i.getName().indexOf(query) == 0) {
//                            if (!checkContain(i.getName())) {
                                if (!searchList.containsKey(key)) {
                                    searchList.put(key, new HashSet<Ingredient>());
                                }
                                searchList.get(key).add(i);

                        }
                    }
                }
                itemFragmentListener.setFragment(true, Category.ALL, true, searchList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus)
            {
                BottomNavigationView menuView = getActivity().findViewById(R.id.bottom_menu);
                if(hasFocus){

                    menuView.setVisibility(View.INVISIBLE);
                }
                else if(!hasFocus)
                {
                    menuView.setVisibility(View.VISIBLE);
                }
            }
        });

        initializeList(allIngredients);
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
        itemFragmentListener.setFragment(true, category, false, null );
    }

    public HashMap<Category, ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }

    public void initializeList(HashMap<String, List<Ingredient>> allIngrediens) {
        if(allIngredients == null){
            //Log.d(getTag(), "initialize null" );
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Cannot connect to Server",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        List<String> keys = new ArrayList<>(allIngrediens.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            String[] s = key.split(",");
            s = s[0].split(" ");
            String filename = s[0].toLowerCase();

            int resId = getResources().getIdentifier(filename, "drawable" , getContext().getPackageName());

            ingredients.put(Category.getCategoryName(key), (ArrayList<Ingredient>) allIngrediens.get(key));
            addCategoryItem(Category.getCategoryName(key), getResources().getDrawable(resId, null));

         }

        listAdapter = new CategoryItemAdapter(getContext(), R.layout.item, categories, this);
        listView.setAdapter(listAdapter);
    }

    public void updateTotalIngredients(Category category, HashSet<String> newIngredients) {

        if (!selectedTotalIngredients.keySet().contains(category.getCategoryValue())) {
            selectedTotalIngredients.put(category.getCategoryValue(), new HashSet<String>());
        }
        selectedTotalIngredients.put(category.getCategoryValue(), newIngredients);

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

    public boolean checkContain(String ingredient) {
        for (String key : selectedTotalIngredients.keySet()) {
            for (String s : selectedTotalIngredients.get(key)) {
                if (s.equals(ingredient)) {
                    return  true;
                }
            }
        }
        return false;
    }

    public void setAllIngredients(HashMap<String, List<Ingredient>> allIngrediens) {
        this.allIngredients = allIngrediens;
    }
}
