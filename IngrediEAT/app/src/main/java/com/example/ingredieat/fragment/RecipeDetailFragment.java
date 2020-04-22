package com.example.ingredieat.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ingredieat.entity.Equipment;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.entity.Step;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ingredieat.R;

import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.RecipeDetail;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.ingredieat.setting.Setting.dpToPx;


public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;
    private RecipeDetail recipeDetail;

    private ImageButton like, liked;
    private TextView likes, ratings;
    private RatingBar rating, myRating;

    private boolean showIngredients;

    public RecipeDetailFragment(Recipe recipe, boolean showIngredients){
        this.recipe = recipe;
        recipeDetail = recipe.getRecipeDetail();
        this.showIngredients = showIngredients;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        if (showIngredients)
            loadIngredients(myView);
        else{
            LinearLayout scroll = myView.findViewById(R.id.layout_scroll);
            scroll.removeView(myView.findViewById(R.id.layout_ingredients));
        }
        loadSteps(myView);

        ImageView recipeImg = myView.findViewById(R.id.detail_recipe_img);
        TextView title = myView.findViewById(R.id.detail_recipe_title);
        final Button submit = myView.findViewById(R.id.btn_submit);
        final TextView rating_title = myView.findViewById(R.id.rating_title);

        like = myView.findViewById(R.id.detail_like);
        liked = myView.findViewById(R.id.detail_liked);
        likes = myView.findViewById(R.id.detail_like_count);
        ratings = myView.findViewById(R.id.detail_rating);
        rating = myView.findViewById(R.id.detail_rating_bar);
        myRating = myView.findViewById(R.id.detail_my_rating_bar);

        Glide.with(this).load(recipe.getImg()).into(recipeImg);
        title.setText(recipe.getTitle());
        likes.setText(String.valueOf(recipe.getLikes()));
        ratings.setText(String.valueOf(recipe.getStars()));
        rating.setRating(recipe.getStars());

        if (recipe.getRated()) {  //only one chance to rate
            myRating.setRating(recipe.getUserStars());
            myRating.setIsIndicator(true);
            rating_title.setText("MY RATING: " + recipe.getUserStars());
        }

//        myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                if (b) {
//                    recipe.updateStars(v);
//                    rating.setRating(recipe.getStars());
//                    ratings.setText(String.valueOf(recipe.getStars()));
//                    myRating.setIsIndicator(true);
//                }
//            }
//        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.updateStars(myRating.getRating());
                rating.setRating(recipe.getStars());
                ratings.setText(String.valueOf(recipe.getStars()));
                myRating.setIsIndicator(true);
                submit.setVisibility(View.INVISIBLE);
                rating_title.setText("MY RATING: "+ recipe.getUserStars());
            }
        });

        if (recipe.getLiked()) {
            like.setVisibility(View.INVISIBLE);
            liked.setVisibility(View.VISIBLE);
            submit.setVisibility(View.INVISIBLE);
        }
        else{
            like.setVisibility(View.VISIBLE);
            liked.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.VISIBLE);
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.like();
                like.setVisibility(View.INVISIBLE);
                liked.setVisibility(View.VISIBLE);
                likes.setText(recipe.getLikes());
            }
        });
        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.unlike();
                like.setVisibility(View.VISIBLE);
                liked.setVisibility(View.INVISIBLE);
                likes.setText(recipe.getLikes());
            }
        });


        return myView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void loadIngredients(View myView){
        ChipGroup chipsGroup = (ChipGroup) myView.findViewById(R.id.chip_ingredients);
        String hintText;
        for (Ingredient item : recipeDetail.getUsedIngredients()){
            final Chip chip = new Chip(getContext());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(),
                    null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(chipDrawable);
            chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
            chip.setText(item.getName());
            chipsGroup.addView(chip);
            chip.setChecked(true);
            chip.setClickable(false);
        }
        if (recipeDetail.getMissedIngredients().size()>0) {
            hintText = "You miss " + recipeDetail.getMissedIngredients().size() + " ingredients: ";
            for (int i = 0; i < recipeDetail.getMissedIngredients().size(); i++) {
                final Chip chip = new Chip(getContext());
                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(),
                        null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                chip.setChipDrawable(chipDrawable);
                chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                chip.setText(recipeDetail.getMissedIngredients().get(i).getName());
                if (i > 0)
                    hintText += ", ";
                hintText += recipeDetail.getMissedIngredients().get(i).getName();
                chipsGroup.addView(chip);
                chip.setChecked(false);
                chip.setClickable(false);
            }
        }
        else
            hintText = "You have all "+recipeDetail.getUsedIngredients().size()+" ingredients!";

        TextView hint = myView.findViewById(R.id.hint_ingredients);
        hint.setText(hintText);
    }

    private void loadSteps(View myView){
        LinearLayout container = myView.findViewById(R.id.layout_steps);
        for (int i=0; i< recipeDetail.getSteps().size(); i++){
            LinearLayout myLayout = new LinearLayout(getContext());
            myLayout.setOrientation(LinearLayout.VERTICAL);

            List<Ingredient> stepIngredients = recipeDetail.getSteps().get(i).getIngredients();
            List<Equipment> stepEquipments = recipeDetail.getSteps().get(i).getEquipments();
            String stepInstruction = recipeDetail.getSteps().get(i).getInstruction();

            //set title
            TextView stepTitle = new TextView(getContext());
            stepTitle.setText("STEP "+ (i+1));
            stepTitle.setTextColor(Color.BLACK);
            stepTitle.setTextSize(16);
            stepTitle.setPadding(dpToPx(20,getResources()), dpToPx(10,getResources()),
                    dpToPx(20,getResources()),dpToPx(10,getResources()));
            myLayout.addView(stepTitle);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(dpToPx(20,getResources()), 0,
                    dpToPx(20,getResources()),dpToPx(10,getResources()));

            //set ingredients
            if (stepIngredients.size()>0) {
                LinearLayout ingredientsLine = new LinearLayout(getContext());
                TextView ingredientsTitle = new TextView(getContext());
                ingredientsTitle.setText("Ingredients: ");
                ingredientsTitle.setTextColor(Color.BLACK);
                ingredientsTitle.setTextSize(16);
                TextView ingredients = new TextView(getContext());
                String ingredientsText = "";
                for (int j = 0; j < stepIngredients.size(); j++) {
                    if (j > 0)
                        ingredientsText += ", ";
                    ingredientsText += stepIngredients.get(j).getName();
                }
                ingredients.setText(ingredientsText);
                ingredients.setTextSize(16);
                ingredientsLine.addView(ingredientsTitle);
                ingredientsLine.addView(ingredients);
                myLayout.addView(ingredientsLine, lp);
            }

            //set equipments
            if (stepEquipments.size()>0) {
                LinearLayout equipmentsLine = new LinearLayout(getContext());
                TextView equipmentsTitle = new TextView(getContext());
                equipmentsTitle.setText("Equipments: ");
                equipmentsTitle.setTextColor(Color.BLACK);
                equipmentsTitle.setTextSize(16);
                TextView equipments = new TextView(getContext());
                String equipmentsText = "";
                for (int j = 0; j < stepEquipments.size(); j++) {
                    if (j > 0)
                        equipmentsText += ", ";
                    equipmentsText += stepEquipments.get(j).getName();
                }
                equipments.setText(equipmentsText);
                equipments.setTextSize(16);
                equipmentsLine.addView(equipmentsTitle);
                equipmentsLine.addView(equipments);
                myLayout.addView(equipmentsLine, lp);
            }

            //set instruction
            TextView instruction = new TextView(getContext());
            instruction.setText(stepInstruction);
            instruction.setTextSize(16);

            myLayout.addView(instruction, lp);
            container.addView(myLayout);
        }
    }
}
