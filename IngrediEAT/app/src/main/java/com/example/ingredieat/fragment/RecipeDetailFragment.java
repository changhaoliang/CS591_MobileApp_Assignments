package com.example.ingredieat.fragment;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ingredieat.R;
import com.example.ingredieat.base.Recipe;
import com.example.ingredieat.base.RecipeDetail;

import java.util.Iterator;
import java.util.List;


public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;
    private RecipeDetail recipeDetail;

    private ImageButton like, liked;
    private TextView likes, ratings;
    private RatingBar rating;

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
        loadContents(R.id.layout_ingredients, recipeDetail.getIngredients(), myView);
        //loadContents(R.id.layout_missed, recipeDetail.getMissed(), myView);
        loadContents(R.id.layout_equipments, recipeDetail.getEquipments(), myView);
        loadContents(R.id.layout_steps, recipeDetail.getSteps(), myView);

        ImageView recipeImg = myView.findViewById(R.id.detail_recipe_img);
        TextView title = myView.findViewById(R.id.detail_recipe_title);

        like = myView.findViewById(R.id.detail_like);
        liked = myView.findViewById(R.id.detail_liked);
        likes = myView.findViewById(R.id.detail_like_count);
        ratings = myView.findViewById(R.id.detail_rating);
        rating = myView.findViewById(R.id.detail_rating_bar);

        Glide.with(this).load(recipe.getImg()).into(recipeImg);
        title.setText(recipe.getTitle());
        likes.setText(String.valueOf(recipe.getLikes()));
        ratings.setText(String.valueOf(recipe.getStars()));
        rating.setRating(recipe.getStars());

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    recipe.updataStars(v);
                    rating.setRating(recipe.getStars());
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

    private void loadContents(int layoutId, List<String> contents, View myView){
        LinearLayout myLayout = myView.findViewById(layoutId);
        for (Iterator itr = contents.iterator(); itr.hasNext();){
            TextView newLine = new TextView(getContext());
            newLine.setText(itr.next().toString());
            myLayout.addView(newLine);
        }
    }
}
