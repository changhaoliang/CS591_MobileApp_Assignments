package com.example.ingredieat.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ingredieat.R;
import com.example.ingredieat.base.Category;
import com.example.ingredieat.entity.Ingredient;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class CartFragment extends Fragment {
    private ListView listView;
    private ListAdapter listAdapter;
    private HashMap<String, HashSet<String>> totalIngredients;
    private ArrayList<String> names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setTotalIngredients(HashMap<String, HashSet<String>> ingredients) {
        this.totalIngredients = ingredients;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_cart, container, false);

        if (totalIngredients != null && totalIngredients.size() > 0) {
            this.names = new ArrayList<>(totalIngredients.keySet());
        }
        listView = (ListView) myView.findViewById(R.id.item_ls);
        listView.setAdapter(new MyAdapter(getContext()));
        return myView;
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context context;
        public MyAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public int getCount() {
            return totalIngredients.size();
        }

        @Override
        public Object getItem(int position) {
            return names.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View row;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //Inflater's are awesome, they convert xml to Java Objects!
                row = inflater.inflate(R.layout.cart_item, parent, false);
            } else {
                row = convertView;
            }
            holder = new ViewHolder();
            holder.title = (TextView) row.findViewById(R.id.name_txt);
            holder.chipGroup = (ChipGroup) row.findViewById(R.id.chip_group);
            holder.icon = (ImageView) row.findViewById(R.id.icon_img);

            holder.title.setText(names.get(position));
            if (totalIngredients.get(names.get(position)) != null && totalIngredients.get(names.get(position)).size() > 0) {
                for (String i : totalIngredients.get(names.get(position))) {
                    Chip chip = new Chip(getContext());
                    ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                    chip.setChipDrawable(chipDrawable);
                    chip.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                    chip.setText(i);
                    holder.chipGroup.addView(chip);
                }
            }
            setIcon(names.get(position), holder.icon);

            return row;
        }
    }

    class ViewHolder {
        TextView title;
        ImageView icon;
        ChipGroup chipGroup;
    }

    public void setIcon(String name, ImageView icon) {
        switch (name) {
            case "Meat":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.meat, null));
                break;
            case "Produce":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.vegetables, null));
                break;
            case "Seafood":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.seafood, null));
                break;
            case "Milk, Eggs, Other Dairy":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.dairy2, null));
                break;
            case "Baking":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.baking, null));
                break;
            case "Beverages":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.drinking, null));
                break;
            case "Oil, Vinegar, Salad Dressing":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.oil, null));
                break;
            case "Spices and Seasonings":
                icon.setImageDrawable(getResources().getDrawable(R.drawable.spice, null));
                break;
        }
    }

}
