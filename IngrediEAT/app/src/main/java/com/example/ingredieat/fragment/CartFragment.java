package com.example.ingredieat.fragment;

import android.content.Context;
import android.graphics.ColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Iterator;
import java.util.Map;

/*
    Cart Interface
 */
public class CartFragment extends Fragment {
    private ListView listView;
    private HashMap<String, HashSet<String>> totalIngredients;
    private ArrayList<String> names;
    private Button editButton;
    private LinearLayout bottomLayout;
    private CartFragmentListner cartFragmentListner;
    private Button clearButton;

    private HashMap<String, HashSet<Chip>> totalChips;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface CartFragmentListner {
        void updateSelected(HashMap<String, HashSet<String>> totalIngredients);
    }

    public void setTotalIngredients(HashMap<String, HashSet<String>> ingredients) {
        this.totalIngredients = ingredients;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_cart, container, false);

        // add chips into view by previous status (selected / unselected)
        totalChips = new HashMap<>();
        if (totalIngredients != null && totalIngredients.size() > 0) {
            this.names = new ArrayList<>(totalIngredients.keySet());
            for (String s : totalIngredients.keySet()) {
                totalChips.put(s, new HashSet<Chip>()) ;
                for (String i : totalIngredients.get(s)) {
                    Chip c = new Chip(getContext());
                    ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                    c.setChipDrawable(chipDrawable);
                    c.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                    c.setText(i);
                    c.setChecked(true);
                    totalChips.get(s).add(c);
                }
            }
        }

        listView = (ListView) myView.findViewById(R.id.item_ls);
        listView.setAdapter(new MyAdapter(getContext()));

        editButton = (Button)myView.findViewById(R.id.edit_btn);
        clearButton = (Button)myView.findViewById(R.id.clear_btn);

        bottomLayout = (LinearLayout) myView.findViewById(R.id.bottom);
        if (totalIngredients==null ||totalIngredients.size() == 0  ) {
            clearButton.setEnabled(false);
        } else {
            clearButton.setEnabled(true);
        }

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalIngredients.size() != 0 ) {
                    totalIngredients.clear();
                    names.clear();
                    clearButton.setEnabled(false);
                }

                cartFragmentListner.updateSelected(totalIngredients);
                listView.setAdapter(new MyAdapter(getContext()));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<HashMap.Entry<String, HashSet<Chip>>> iterator = totalChips.entrySet().iterator();

                while (iterator.hasNext()){

                    Map.Entry<String, HashSet<Chip>> item = iterator.next();
                    String key = item.getKey();
                    Iterator<Chip> chipIterator = item.getValue().iterator();
                    while(chipIterator.hasNext()){
                        Chip c = chipIterator.next();
                        if (!c.isChecked()) {
                            if(c.getParent() != null) {
                                ((ChipGroup)c.getParent()).removeView(c);
                            }
                            chipIterator.remove();
                            totalIngredients.get(key).remove(c.getText());

                        }
                    }

                    if (item.getValue().size() == 0) {
                        iterator.remove();
                        totalIngredients.remove(item.getKey());

                        names.remove(item.getKey());
                    }
                }

                cartFragmentListner.updateSelected(totalIngredients);
                listView.setAdapter(new MyAdapter(getContext()));
            }
        });
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
            if(totalIngredients == null) return 0;
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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.cart_item, parent, false);
            } else {
                row = convertView;
            }

            holder = new ViewHolder();
            holder.title = (TextView) row.findViewById(R.id.name_txt);
            holder.chipGroup = (ChipGroup) row.findViewById(R.id.chip_group);
            holder.icon = (ImageView) row.findViewById(R.id.icon_img);
            holder.title.setText(names.get(position));

            if (holder.chipGroup.getChildCount() > 0) {
                holder.chipGroup.removeAllViews();
            }

            // update chips, synchronize data if user changes selected in other fragment
            String key = names.get(position);
            if (totalIngredients.get(key) != null && totalIngredients.get(key).size() > 0) {
                HashSet<Chip> chips = totalChips.get(key);
                for (Chip c : chips) {
                    if(c.getParent() != null) {
                        ((ChipGroup)c.getParent()).removeView(c);
                    }
                    holder.chipGroup.addView(c);
                }
            }
            setIcon(names.get(position), holder.icon);

            return row;
        }
    }

    /**
     * One category item view content
     */
    class ViewHolder {
        TextView title;
        ImageView icon;
        ChipGroup chipGroup;
    }

    /**
     * set icon by category
     */
    public void setIcon(String name, ImageView icon) {
        String[] s = name.split(",");
        s = s[0].split(" ");
        String filename = s[0].toLowerCase();

        int resId = getResources().getIdentifier(filename, "drawable" , getContext().getPackageName());

        icon.setImageDrawable(getResources().getDrawable(resId, null));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartFragmentListner = (CartFragmentListner) context;
    }
}
