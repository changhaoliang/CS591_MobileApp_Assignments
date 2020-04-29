package com.example.ingredieat.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ingredieat.adapter.FavoriteAdapter;
import com.example.ingredieat.adapter.RecipeAdapter;
import com.example.ingredieat.entity.Equipment;
import com.example.ingredieat.entity.Ingredient;
import com.example.ingredieat.utils.HttpUtils;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.bumptech.glide.Glide;
import com.example.ingredieat.R;

import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.entity.RecipeDetail;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ingredieat.setting.Setting.dpToPx;
import static com.example.ingredieat.setting.Setting.googleId;


public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;
    private RecipeAdapter recipeAdapter;
    private FavoriteAdapter favoriteAdapter;
    private RecipeDetail recipeDetail;

    private ImageButton like, liked;
    private TextView likes, ratings;//show num of likes, average rating points
    private RatingBar rating, myRating;

    private DetailFragmentListener detailFragmentListener;

    private boolean showIngredients;    //True from recipe fragment, False from favorate fragment

    public RecipeDetailFragment(Recipe recipe, RecipeAdapter recipeAdapter){
        this.recipe = recipe;
        this.recipeAdapter = recipeAdapter;
        recipeDetail = recipe.getRecipeDetail();
        this.showIngredients = true;
    }

    public RecipeDetailFragment(Recipe recipe, FavoriteAdapter favoriteAdapter){
        this.recipe = recipe;
        this.favoriteAdapter = favoriteAdapter;
        recipeDetail = recipe.getRecipeDetail();
        this.showIngredients = false;
    }

    public interface DetailFragmentListener {
        void addLikes(Recipe recipe);
        void removeLikes(Recipe recipe);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        if (showIngredients) //recipe fragment
            loadIngredients(myView);
        else{   //favorate fragment
            LinearLayout scroll = myView.findViewById(R.id.layout_scroll);
            scroll.removeView(myView.findViewById(R.id.layout_ingredients));
        }
        loadSteps(myView);

        final ImageView recipeImg = myView.findViewById(R.id.detail_recipe_img);
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
        if (recipe.getStars() == 0)
            ratings.setText("");
        else
            ratings.setText(String.valueOf(recipe.getStars()));
        rating.setRating(recipe.getStars());

        if (recipe.getRated()) {  //only one chance to rate
            myRating.setRating(recipe.getUserStars());
            myRating.setIsIndicator(true);
            rating_title.setText("MY RATING: " + recipe.getUserStars());
            submit.setVisibility(View.INVISIBLE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.updateUserStars(myRating.getRating());
                myRating.setIsIndicator(true);
                submit.setVisibility(View.INVISIBLE);
                rating_title.setText("MY RATING: "+ recipe.getUserStars());
                Map<String, String> params = new HashMap<>();
                params.put("googleId", googleId);
                params.put("recipeId", String.valueOf(recipe.getId()));
                params.put("rated", String.valueOf(recipe.getRated()));
                params.put("userStars", String.valueOf(recipe.getUserStars()));
                HttpUtils.postRequest("/home/ratingRecipe", params, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        ResponseBody body = response.body();
                        if(body != null) {
                            String data = body.string();
                            // Here we use Fastjson to parse json string.
                            float stars = Float.valueOf(data);
                            recipe.setStars(stars);
                            Message msg = Message.obtain();
                            handler2.sendMessage(msg);
                        }
                    }
                });
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
                likes.setText(recipe.getLikes());
                like.setVisibility(View.INVISIBLE);
                liked.setVisibility(View.VISIBLE);
                likes.setText(recipe.getLikes());
                detailFragmentListener.addLikes(recipe);

                Map<String, String> params = new HashMap<>();
                params.put("googleId", googleId);
                params.put("recipeId", String.valueOf(recipe.getId()));
                params.put("liked", String.valueOf(recipe.getLiked()));
                HttpUtils.postRequest("/home/updateUserRecipeLiked", params, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Message msg = Message.obtain();
                        handler1.sendMessage(msg);
                    }
                });
            }
        });
        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.unlike();
                likes.setText(recipe.getLikes());
                like.setVisibility(View.VISIBLE);
                liked.setVisibility(View.INVISIBLE);
                likes.setText(recipe.getLikes());
                detailFragmentListener.removeLikes(recipe);

                Map<String, String> params = new HashMap<>();
                params.put("googleId", googleId);
                params.put("recipeId", String.valueOf(recipe.getId()));
                params.put("liked", String.valueOf(recipe.getLiked()));
                HttpUtils.postRequest("/home/updateUserRecipeLiked", params, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Message msg = Message.obtain();
                        handler1.sendMessage(msg);
                    }
                });
            }
        });

        return myView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        detailFragmentListener = (DetailFragmentListener) context;
    }

    //used & unused ingredients
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

    //load recipe contents
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

    //update recipe fragment
    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (showIngredients)
                recipeAdapter.notifyDataSetChanged();
            else {
                favoriteAdapter.notifyDataSetChanged();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            rating.setRating(recipe.getStars());
            ratings.setText(String.valueOf(recipe.getStars()));
            if (showIngredients)
                recipeAdapter.notifyDataSetChanged();
            else {
                favoriteAdapter.notifyDataSetChanged();
            }
        }
    };
}
