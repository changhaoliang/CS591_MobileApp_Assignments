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

//    public void setClickBorder(CategoryItem item, View view) {
//        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
//        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
//
//        cardView.setBackgroundColor(getContext().getResources().getColor(R.color.card_border));
//        item.setSelected(true);
//        linearLayout.setBackgroundColor(Color.WHITE);
//    }
//
//    public void cleanBorder(CategoryItem item, View view) {
//        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
//        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
//        cardView.setBackgroundColor(Color.WHITE);
//        linearLayout.setBackgroundColor(Color.TRANSPARENT);
//        item.setSelected(false);
//
//        Setting.count -= 2;
//        if (Setting.shortClickFlag && Setting.count == 0) {
//            Setting.shortClickFlag = false;
//            Setting.longClickFlag = false;
//        }
//    }
//
//    public void setLongClickColor(CategoryItem item, View view) {
//        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
//        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
//        cardView.setBackgroundColor(getContext().getResources().getColor(R.color.card_border));
//        item.setSelected(true);
//        System.out.println(item.getName());
//    }

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
//
//        if (Setting.shortClickFlag && categoryItem.getSeclected()) {
//            setClickBorder(categoryItem, view);
//        }
//        if (Setting.longClickFlag && categoryItem.getSeclected()) {
//            setLongClickColor(categoryItem, view);
//        }
//        else {
//            cardView.setBackgroundColor(Color.WHITE);
//            final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
//            linearLayout.setBackgroundColor(Color.TRANSPARENT);
//        }

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
//
//                String[] ingredients = new String[]{"butter", "egg", "milk", "american cheese", "cheddar", "sour cream", "yogurt", "cream cheese"};
                myClickListener.clickListener(v, category);


//                if (!Setting.longClickFlag && Setting.count == 0) {
//                    Setting.shortClickFlag = true;
//                }
//
//                Setting.count++;
//                // 单击状态
//                if (Setting.shortClickFlag) {
//                    if (categoryItem.getSeclected()) {
//                        cleanBorder(categoryItem, view);
//                    } else {
//                        setClickBorder(categoryItem, view);
//                    }
//                } else { //长按状态
//                    if (categoryItem.getSeclected()) {
//                        cleanBorder(categoryItem, view);
//                    } else {
//                        setLongClickColor(categoryItem, view);
//                        System.out.println("345");
//                    }
//                }
            }
        });

//        cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (Setting.longClickFlag) {
//                    return false;
//                }
//
//                if (Setting.count == 0) {
//                    Setting.longClickFlag = true;
//                }
//                Setting.count++;
//
//                if (!Setting.shortClickFlag) {
//                    myLongClickListner.longClickListner(v);
//                    setLongClickColor(categoryItem, view);
//                    System.out.println("123");
//                } else {
//                    setClickBorder(categoryItem, view);
//                }
//                return true;
//            }
//        });

        return view;
    }


}
