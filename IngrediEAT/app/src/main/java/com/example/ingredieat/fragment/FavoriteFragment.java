package com.example.ingredieat.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.ingredieat.R;
import com.example.ingredieat.adapter.FavoriteAdapter;
import com.example.ingredieat.entity.Recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.ingredieat.setting.Setting.dpToPx;


public class FavoriteFragment extends Fragment implements FavoriteAdapter.MyClickListener {
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private HashSet<Recipe> favoriteRecipes;

    private FavoriteFragmentListener favoriteFragmentListener;

    @Override
    public void clickListener(View v, Recipe recipe) {
        favoriteFragmentListener.showFavoriteDetails(recipe, favoriteAdapter);
    }

    @Override
    public void likeListener(View v, Recipe recipe, boolean like) {
        if (like)
            favoriteFragmentListener.addLikes(recipe);
        else
            favoriteFragmentListener.removeLikes(recipe);
    }

    public interface FavoriteFragmentListener {
        void showFavoriteDetails(Recipe recipe, FavoriteAdapter favoriteAdapter);
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
        View myView = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = myView.findViewById(R.id.favorite_recycler_view);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(favoriteRecipes == null) {
            favoriteRecipes = new HashSet<>();
        }
        favoriteAdapter = new FavoriteAdapter(getContext(), favoriteRecipes, this);
        recyclerView.setAdapter(favoriteAdapter);

        return myView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        favoriteFragmentListener = (FavoriteFragmentListener) context;
    }

    public void setRecipes(HashSet<Recipe> recipes){
        this.favoriteRecipes = recipes;
        if (favoriteAdapter!=null) {
            favoriteAdapter.setRecipes(recipes);
            favoriteAdapter.notifyDataSetChanged();
        }
    }

    public HashSet<Recipe> getRecipes() {
        return favoriteRecipes;
    }

    public FavoriteAdapter getRecipeAdapter() {
        return favoriteAdapter;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spacing;
        private final int columns = 1;

        public GridSpacingItemDecoration() {
            this.spacing = dpToPx(5, getResources());  //10dp
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

                outRect.left = spacing;
                outRect.right = spacing;
            //top & bottom margin
            if (position < columns)
                outRect.top = spacing;
            outRect.bottom = spacing;
        }
    }
}
