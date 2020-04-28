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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.bumptech.glide.Glide;
import com.example.ingredieat.R;
import com.example.ingredieat.entity.Recipe;
import com.example.ingredieat.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.example.ingredieat.setting.Setting.googleId;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
/*
    Similar to recipe adapter but use different view holder
 */
    private List<Recipe> recipes;
    private Context context;
    private MyClickListener myClickListener;
    private Callback callback;

    public FavoriteAdapter(Context context, HashSet<Recipe> recipes, MyClickListener myClickListener) {
        this.context = context;
        this.recipes = new ArrayList<>(recipes);
        this.myClickListener = myClickListener;
        callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        };
    }

    public interface MyClickListener {
        public void clickListener(View v, Recipe recipe);

        public void likeListener(View v, Recipe recipe, boolean like);
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_recipe, parent, false);

        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteHolder holder, final int position) {
        Log.d("Boston University", "onBindViewHolder");
        final Recipe recipe = recipes.get(position);
        holder.title.setText(recipe.getTitle());
        holder.likes.setText(recipe.getLikes());
        holder.rating_bar.setRating(recipe.getStars());
        if (recipe.getStars() == 0)
            holder.rating_point.setText("");
        else
            holder.rating_point.setText(String.valueOf(recipe.getStars()));

        Glide.with(context).load(recipe.getImg()).into(holder.recipeImg);

        if (recipe.getLiked()) {
            holder.like.setVisibility(View.INVISIBLE);
            holder.liked.setVisibility(View.VISIBLE);
        } else {
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
                myClickListener.likeListener(view, recipes.get(position), true);
                updateLikeStatus(recipe);
            }
        });
        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.unlike();
                holder.like.setVisibility(View.VISIBLE);
                holder.liked.setVisibility(View.INVISIBLE);
                holder.likes.setText(recipe.getLikes());
                myClickListener.likeListener(view, recipes.get(position), false);
                updateLikeStatus(recipe);
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

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        private ImageButton like, liked;
        private TextView title, likes, rating_point;
        private RatingBar rating_bar;
        private ImageView recipeImg;
        private CardView cardView;

        public FavoriteHolder(View view) {
            super(view);
            recipeImg = view.findViewById(R.id.favorite_img);
            like = view.findViewById(R.id.favorite_like);
            liked = view.findViewById(R.id.favorite_liked);
            title = view.findViewById(R.id.favorite_title);
            likes = view.findViewById(R.id.favorite_like_count);
            rating_bar = view.findViewById(R.id.favorite_rating_bar);
            rating_point = view.findViewById(R.id.favorite_rating);
            cardView = view.findViewById(R.id.favorite_recipe);
        }
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setRecipes(HashSet<Recipe> recipes) {
        this.recipes = new ArrayList<>(recipes);
    }

    private void updateLikeStatus(Recipe recipe) {
        Map<String, String> params = new HashMap<>();
        params.put("googleId", googleId);
        params.put("recipeId", String.valueOf(recipe.getId()));
        params.put("liked", String.valueOf(recipe.getLiked()));
        HttpUtils.postRequest("/home/updateUserRecipeLiked", params, callback);
    }
}
