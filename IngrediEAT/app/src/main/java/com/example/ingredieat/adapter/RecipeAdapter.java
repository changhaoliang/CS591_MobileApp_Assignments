package com.example.ingredieat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredieat.R;
import com.example.ingredieat.entity.Recipe;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private List<Recipe> recipes;
    private Context context;
    private MyClickListener myClickListener;

    public RecipeAdapter(Context context, List<Recipe> recipes, MyClickListener myClickListener) {
        this.context = context;
        this.recipes = recipes;
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void clickListener(View v, Recipe recipe);
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe, parent, false);

        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeHolder holder, final int position) {
        Log.d("Boston University", "onBindViewHolder");
        final Recipe recipe = recipes.get(position);
        holder.title.setText(recipe.getTitle());
        holder.likes.setText(recipe.getLikes());
        holder.rating.setRating(recipe.getStars());

        Glide.with(context).load(recipe.getImg()).into(holder.recipeImg);

        if (recipe.getLiked()) {
            holder.like.setVisibility(View.INVISIBLE);
            holder.liked.setVisibility(View.VISIBLE);
        }
        else{
            holder.like.setVisibility(View.VISIBLE);
            holder.liked.setVisibility(View.INVISIBLE);
        }
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.like();
                holder.like.setVisibility(View.INVISIBLE);
                holder.liked.setVisibility(View.VISIBLE);
                holder.likes.setText(recipe.getLikes());
            }
        });
        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.unlike();
                holder.like.setVisibility(View.VISIBLE);
                holder.liked.setVisibility(View.INVISIBLE);
                holder.likes.setText(recipe.getLikes());
            }
        });
        holder.recipeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.clickListener(view, recipes.get(position));
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.clickListener(view, recipes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        private ImageButton like, liked;
        private TextView title, likes;
        private RatingBar rating;
        private ImageView recipeImg;
        private CardView cardView;

        public RecipeHolder(View view) {
            super(view);
            recipeImg = view.findViewById(R.id.recipe_img);
            like = view.findViewById(R.id.like);
            liked = view.findViewById(R.id.liked);
            title = view.findViewById(R.id.recipe_title);
            likes = view.findViewById(R.id.like_count);
            rating = view.findViewById(R.id.rating_bar);
            cardView = view.findViewById(R.id.recipe);
        }
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
    }
}
