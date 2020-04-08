package com.example.ingredieat.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ingredieat.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;

public class IngredientsFragment extends Fragment {
    private ArrayList<String> ingredients;
    private ChipGroup chipsGroup;
    private HashSet<String> selectedIngredients;
    private String category;
    IngredientFragmentListner ingredientFragmentListner;

    public interface IngredientFragmentListner {
        boolean getSelected(String category, String ingredient);
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


        if (ingredients.size() != 0) {
            for (String ingredient : ingredients) {
                final Chip chip = new Chip(getContext());
                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                chip.setChipDrawable(chipDrawable);
                chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                chip.setText(ingredient);
                chipsGroup.addView(chip);

                if (ingredientFragmentListner.getSelected(category, chip.getText().toString())) {
                    chip.setChecked(true);
                }
            }
        }
        return myView;
    }

    public void setIngredients(ArrayList<String> ingredients) {
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
        ingredientFragmentListner = (IngredientFragmentListner) context;
    }

    public void setView(String category) {
        this.category = category;
    }
}
