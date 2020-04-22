package com.example.ingredieat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import okhttp3.ResponseBody;

import com.example.ingredieat.R;
import com.example.ingredieat.entity.Recipe;

import com.bumptech.glide.Glide;
import com.example.ingredieat.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ingredieat.setting.Setting.googleId;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private List<Recipe> recipes;
    private Context context;
    private MyClickListener myClickListener;
    private RecipeHolder holder;

    public RecipeAdapter(Context context, List<Recipe> recipes, MyClickListener myClickListener) {
        this.context = context;
        this.recipes = recipes;
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void clickListener(View v, Recipe recipe);
        public void updateView();
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
        this.holder = holder;
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
                        ResponseBody body = response.body();
                        if(body != null) {
                            String data = body.string();
                            int likes = Integer.valueOf(data);
                            recipe.setLikes(likes);
                            Message msg = Message.obtain();
                            handler1.sendMessage(msg);
                        }
                    }
                });
            }
        });

        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe.unlike();
                holder.like.setVisibility(View.VISIBLE);
                holder.liked.setVisibility(View.INVISIBLE);

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
                        ResponseBody body = response.body();
                        if(body != null) {
                            String data = body.string();
                            int likes = Integer.valueOf(data);
                            recipe.setLikes(likes);
                            Message msg = Message.obtain();
                            handler1.sendMessage(msg);
                        }
                    }
                });
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

    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            myClickListener.updateView();
        }
    };
}
