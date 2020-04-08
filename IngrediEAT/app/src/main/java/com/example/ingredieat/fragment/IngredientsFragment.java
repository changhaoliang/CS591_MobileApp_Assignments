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

import com.example.ingredieat.R;
import com.example.ingredieat.base.Category;
import com.example.ingredieat.entity.Ingredient;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.appcompat.widget.SearchView;

public class IngredientsFragment extends Fragment {
    private List<Ingredient> ingredients;
    private List<Ingredient> searchIngredients;

    private ChipGroup chipsGroup;
    private HashSet<String> selectedIngredients;
    private Category category;
    private Button add_btn;
    private SearchView searchView;

    private IngredientFragmentListener ingredientFragmentListener;

    public interface IngredientFragmentListener {
        boolean getSelected(Category category, String ingredient);
        void updateTotalSelected();
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
        selectedIngredients = new HashSet<>();
        chipsGroup = (ChipGroup) myView.findViewById(R.id.chip_group);
        add_btn = (Button) myView.findViewById(R.id.button_ok);
        searchView = (SearchView) myView.findViewById(R.id.search);


        if (ingredients.size() != 0) {
            setChips(ingredients);
        }

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientFragmentListener.updateTotalSelected();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchIngredients = new ArrayList<>();
                for (Ingredient i : ingredients) {
                    String name = i.getName();
                    String[] words = name.split(" ");
                    for (String word : words) {
                        if (word.indexOf(newText) == 0) {
                            searchIngredients.add(i);
                        }
                    }
                }
                if (searchIngredients.size() != 0) {
                    setChips(searchIngredients);
                }
                return false;
            }
        });
        return myView;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public HashSet<String> getSelectedIngredients() {
        for (int i = 0; i < chipsGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipsGroup.getChildAt(i);
            String s = chip.getText().toString();

            if (!chip.isChecked() && selectedIngredients.contains(s)) {

                selectedIngredients.remove(s);
            }
            if (chip.isChecked()) {
                System.out.println(s);
                selectedIngredients.add(s);
            }
        }
        return selectedIngredients;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ingredientFragmentListener = (IngredientFragmentListener) context;
    }

    public void setView(Category category) {
        this.category = category;
    }


    private void setChips(List<Ingredient> ingredients) {
        if (chipsGroup.getChildCount() > 0) {
            chipsGroup.removeAllViews();
        }
        for (Ingredient ingredient : ingredients) {
            System.out.println(ingredient.getName());
            final Chip chip = new Chip(getContext());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(chipDrawable);
            chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
            chip.setText(ingredient.getName());
            chipsGroup.addView(chip);

            if (ingredientFragmentListener.getSelected(category, chip.getText().toString())) {
                chip.setChecked(true);
            }
        }
    }

}
