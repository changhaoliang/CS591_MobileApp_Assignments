package com.example.ingredieat.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ingredieat.R;
import com.example.ingredieat.adapter.RecipeAdapter;
import com.example.ingredieat.base.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeFragment extends Fragment implements RecipeAdapter.MyClickListener{
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;

    private RecipeFragmentListener recipeFragmentListener;

    @Override
    public void clickListener(View v, Recipe recipe) {
        recipeFragmentListener.showDetails(recipe);
    }

    public interface RecipeFragmentListener {
        public void showDetails(Recipe recipe);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_recipe, container, false);
        recyclerView = myView.findViewById(R.id.recycler_view);

        //recipes = new ArrayList<Recipe>();
        recipeAdapter = new RecipeAdapter(getContext(), recipes, this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recipeAdapter);

        return myView;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recipeFragmentListener = (RecipeFragmentListener) context;
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        //recipeAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spacing;
        private final int columns = 2;

        public GridSpacingItemDecoration() {
            this.spacing = dpToPx(10);  //10dp
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);

            //left & right margin
            if (position % columns == 0){
                outRect.left = spacing;
                outRect.right = spacing/columns;
            }
            else{
                outRect.left = spacing/columns;
                outRect.right = spacing;
            }

            //top & bottom margin
            if (position < columns)
                outRect.top = spacing;
            outRect.bottom = spacing;
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
