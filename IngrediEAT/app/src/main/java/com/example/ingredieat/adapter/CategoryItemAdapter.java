package com.example.ingredieat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.ingredieat.base.Category;
import com.example.ingredieat.base.CategoryItem;
import com.example.ingredieat.R;

import java.util.List;

public class CategoryItemAdapter extends ArrayAdapter<CategoryItem> {
    private int layoutId;
    private List<CategoryItem> categoryItems;

    private MyClickListener myClickListener;

    public interface MyClickListener {
        public void clickListener(View v, Category category);
    }

    public CategoryItemAdapter(Context context, int layoutId, List<CategoryItem> list, MyClickListener listener) {
        super(context, layoutId, list);
        this.layoutId = layoutId;
        this.categoryItems = list;
        this.myClickListener = listener;
    }

    @Override
    public int getCount() {
        return categoryItems.size();
    }

    @Nullable
    @Override
    public CategoryItem getItem(int position) {
        return categoryItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final CategoryItem categoryItem = getItem(position);

        final View view;
        if (convertView == null) {
            view = inflater.inflate(layoutId, parent, false);
        } else {
            view = convertView;
        }
        final CardView cardView = (CardView) view.findViewById(R.id.card_view);
        final ImageView imageButton = (ImageView) view.findViewById(R.id.imageButton);
        final TextView nameText = (TextView) view.findViewById(R.id.name_txt);

        imageButton.setImageDrawable(categoryItem.getPicture());
        nameText.setText(categoryItem.getCategory().getCategoryValue());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TODO: image button click");
            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = categoryItem.getCategory();
                myClickListener.clickListener(v, category);
            }
        });

        return view;
    }


}
