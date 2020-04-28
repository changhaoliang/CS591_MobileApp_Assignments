package com.example.ingredieat.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ingredieat.R;
import com.example.ingredieat.base.Category;
import com.example.ingredieat.entity.Ingredient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.widget.SearchView;

/*
    Ingredient Fragment (Fragment shown after clicking a categroy)
 */
public class IngredientsFragment extends Fragment {
    private List<Ingredient> ingredients;
    private List<Ingredient> searchIngredients;

    private ChipGroup chipsGroup;
    private HashMap<String, HashSet<String>> selectedIngredients;
    private HashMap<String, HashSet<String>> selectedSearchIngredients;
    private Category category;
    private LinearLayout layout;
    private Button add_btn;
    private SearchView searchView;
    private TextView title;
    private boolean ifSearch;

    private HashMap<String, List<Ingredient>> allIngredients;

    private IngredientFragmentListener ingredientFragmentListener;

    public interface IngredientFragmentListener {
        boolean getSelected(Category category, String ingredient);
        void updateTotalSelected(Category category);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        selectedIngredients = new HashMap<>();
        layout = (LinearLayout)myView.findViewById(R.id.layout);
        chipsGroup = (ChipGroup) myView.findViewById(R.id.chip_group);
        add_btn = (Button) myView.findViewById(R.id.button_ok);
        searchView = (SearchView) myView.findViewById(R.id.search);
        title = (TextView) myView.findViewById(R.id.title_txt);

        // For general search -> Category.ALL check
        if (category.equals(Category.ALL)) {
            layout.removeView(searchView);
            title.setText("Search Results");
        }

        else {
            title.setText(category.getCategoryValue());
            selectedIngredients.put(category.getCategoryValue(), new HashSet<String>());
        }

        if (ingredients.size() != 0) {
            setChips(ingredients);
        }

        // add ingredients
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientFragmentListener.updateTotalSelected(category);
            }
        });

        // search ingredients from current fragment
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                HashSet<Ingredient> searchIngredientsSet = new HashSet<>();
                searchIngredients = new ArrayList<>();
                for (Ingredient i : ingredients) {
                    String name = i.getName();
                    String[] words = name.split(" ");
                    for (String word : words) {
                        if (word.indexOf(newText) == 0) {
                            searchIngredientsSet.add(i);
                        }
                    }
                }
                searchIngredients.addAll(searchIngredientsSet);
                Collections.sort(searchIngredients, new Comparator<Ingredient>() {
                    @Override
                    public int compare(Ingredient o1, Ingredient o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                if (searchIngredients.size() != 0) {
                    setChips(searchIngredients);
                }
                ifSearch = true;
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
        return myView;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * get selected ingredients -> Update Cart Fragment
     */
    public HashMap<String, HashSet<String>> getSelectedIngredients() {
        System.out.println("================================================");
        for (String key : selectedIngredients.keySet()) {
            for (String i  : selectedIngredients.get(key)) {
                System.out.println(key + "===" + i);
            }
        }
        for (int i = 0; i < chipsGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipsGroup.getChildAt(i);
            String s = chip.getText().toString();

            if (!category.equals(Category.ALL)) {
                if (chip.isChecked()) {
                    selectedIngredients.get(category.getCategoryValue()).add(s);
                }
            } else {
                for (String c : allIngredients.keySet()) {
                    for (Ingredient ingredient : allIngredients.get(c)) {
                        if (chip.isChecked() && ingredient.getName().equals(s)) {
                            if (!selectedIngredients.containsKey(c)) {
                                selectedIngredients.put(c, new HashSet<String>());
                            }
                            selectedIngredients.get(c).add(s);
                        }
                        if (!chip.isChecked() && ingredient.getName().equals(s)) {
                        }
                    }
                }
            }
        }
        return selectedIngredients;
    }

    public HashMap<String, HashSet<String>> getSelectedSearchIngredients() {
        for (int i = 0; i < chipsGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipsGroup.getChildAt(i);
            String s = chip.getText().toString();

            if (!category.equals(Category.ALL)) {
                if (chip.isChecked()) {
                    selectedIngredients.get(category.getCategoryValue()).add(s);
                }
            } else {
                for (String c : allIngredients.keySet()) {
                    for (Ingredient ingredient : allIngredients.get(c)) {
                        if (chip.isChecked() && ingredient.getName().equals(s)) {
                            if (!selectedIngredients.containsKey(c)) {
                                selectedIngredients.put(c, new HashSet<String>());
                            }
                            selectedIngredients.get(c).add(s);
                        }
                    }
                }
            }
        }
        return selectedIngredients;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ingredientFragmentListener = (IngredientFragmentListener) context;
    }

    /**
     * set chipgroups status
     */
    private void setChips(List<Ingredient> ingredients) {
        if (chipsGroup.getChildCount() > 0) {
            chipsGroup.removeAllViews();
        }
        for (Ingredient ingredient : ingredients) {
            final Chip chip = new Chip(getContext());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(chipDrawable);
            chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
            chip.setText(ingredient.getName());
            chipsGroup.addView(chip);

            if (category.equals(Category.ALL)) {
                for (Category c : Category.values()) {
                    if (ingredientFragmentListener.getSelected(c, chip.getText().toString())) {
                        chip.setChecked(true);
                        chip.setEnabled(false);
                    }
                }
            } else {
                if (ingredientFragmentListener.getSelected(category, chip.getText().toString())) {
                    chip.setChecked(true);
                    chip.setEnabled(false);
                }
            }
        }
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAllIngredients(HashMap<String, List<Ingredient>> allIngredients) {
        this.allIngredients = allIngredients;
    }

    public void setIfSearch(boolean flag) {
        ifSearch = flag;
    }

}
