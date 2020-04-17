package com.example.ingredieat.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ingredieat.R;
import com.example.ingredieat.base.Recipe;
import com.example.ingredieat.base.RecipeDetail;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.ingredieat.setting.Setting.dpToPx;


public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;
    private RecipeDetail recipeDetail;

    private ImageButton like, liked;
    private TextView likes, ratings;
    private RatingBar rating, myRating;

    //private RecipeDetailFragmentListener RDFL;

    public RecipeDetailFragment(Recipe recipe){
        this.recipe = recipe;
        this.recipeDetail = new RecipeDetail(recipe);
    }

//    public interface RecipeDetailFragmentListener {
//        public void updateLikes();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        loadIngredients(myView);
        loadEquipment(myView);
        loadSteps(myView);

        ImageView recipeImg = myView.findViewById(R.id.detail_recipe_img);
        TextView title = myView.findViewById(R.id.detail_recipe_title);

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
            myRating.setRating(recipe.getUserStar());
            myRating.setIsIndicator(true);
        }

        myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    recipe.updataStars(v);
                    rating.setRating(recipe.getStars());
                    ratings.setText(String.valueOf(recipe.getStars()));
                    myRating.setIsIndicator(true);
                }
            }
        });

        if (recipe.getLiked()) {
            like.setVisibility(View.INVISIBLE);
            liked.setVisibility(View.VISIBLE);
        }
        else{
            like.setVisibility(View.VISIBLE);
            liked.setVisibility(View.INVISIBLE);
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
        //RDFL = (RecipeDetailFragmentListener) context;
    }

    private void loadIngredients(View myView){
        ChipGroup chipsGroup = (ChipGroup) myView.findViewById(R.id.chip_ingredients);
        String hintText;
        for (String item : recipeDetail.getIngredients()){
            final Chip chip = new Chip(getContext());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(),
                    null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
            chip.setChipDrawable(chipDrawable);
            chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
            chip.setText(item);
            chipsGroup.addView(chip);
            chip.setChecked(true);
            chip.setClickable(false);
        }
        if (recipeDetail.getMissed().size()>0) {
            hintText = "You miss " + recipeDetail.getMissed().size() + " ingredients: ";
            for (int i = 0; i < recipeDetail.getMissed().size(); i++) {
                final Chip chip = new Chip(getContext());
                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(),
                        null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                chip.setChipDrawable(chipDrawable);
                chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                chip.setText(recipeDetail.getMissed().get(i));
                if (i > 0)
                    hintText += ", ";
                hintText += recipeDetail.getMissed().get(i);
                chipsGroup.addView(chip);
                chip.setChecked(false);
                chip.setClickable(false);
            }
        }
        else
            hintText = "You have all "+recipeDetail.getIngredients().size()+" ingredients!";

        TextView hint = myView.findViewById(R.id.hint_ingredients);
        hint.setText(hintText);
    }

    private void loadEquipment(View myView){
        LinearLayout myLayout = myView.findViewById(R.id.layout_equipments);
        for (Iterator itr = recipeDetail.getEquipments().iterator(); itr.hasNext();){
            TextView newLine = new TextView(getContext());
            newLine.setText(itr.next().toString());
            newLine.setTextSize(16);
            myLayout.addView(newLine);
        }
    }

    private void loadSteps(View myView){
        LinearLayout container = myView.findViewById(R.id.layout_steps);
        for (int i=0; i<recipeDetail.getSteps().size(); i++){
            LinearLayout myLayout = new LinearLayout(getContext());
            myLayout.setOrientation(LinearLayout.VERTICAL);
            TextView step = new TextView(getContext());
            step.setText("STEP "+ (i+1));
            step.setTextColor(Color.BLACK);
            step.setTextSize(16);
            step.setPadding(dpToPx(20,getResources()), dpToPx(10,getResources()),
                    dpToPx(20,getResources()),dpToPx(10,getResources()));

            TextView instruction = new TextView(getContext());
            instruction.setText(recipeDetail.getSteps().get(i));
            instruction.setTextSize(16);
            instruction.setPadding(dpToPx(20,getResources()), 0,
                    dpToPx(20,getResources()),dpToPx(10,getResources()));

            myLayout.addView(step);
            myLayout.addView(instruction);
            container.addView(myLayout);
        }
    }
}
