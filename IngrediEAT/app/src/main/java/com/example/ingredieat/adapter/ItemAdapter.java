package com.example.ingredieat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.ingredieat.entity.Item;
import com.example.ingredieat.R;
import com.example.ingredieat.setting.Setting;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int layoutId;
    private List<Item> items;

    private MyClickListner myClickListner;

    public interface MyClickListner {
        public void clickListner(View v, String category);
    }

    public ItemAdapter(Context context, int layoutId, List<Item> list, MyClickListner listner){
        super(context,layoutId,list);
        this.layoutId = layoutId;
        this.items = list;
        this.myClickListner = listner;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

//    public void setClickBorder(Item item, View view) {
//        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
//        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
//
//        cardView.setBackgroundColor(getContext().getResources().getColor(R.color.card_border));
//        item.setSelected(true);
//        linearLayout.setBackgroundColor(Color.WHITE);
//    }
//
//    public void cleanBorder(Item item, View view) {
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
//    public void setLongClickColor(Item item, View view) {
//        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
//        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
//        cardView.setBackgroundColor(getContext().getResources().getColor(R.color.card_border));
//        item.setSelected(true);
//        System.out.println(item.getName());
//    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Item item = getItem(position);

        final View view;
        if (convertView == null) {
            view = inflater.inflate(layoutId, parent, false);
        }
        else {
            view = convertView;
        }
        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
        final ImageView imageButton = (ImageView)view.findViewById(R.id.imageButton);
        final TextView nameText = (TextView)view.findViewById(R.id.name_txt);
//
//        if (Setting.shortClickFlag && item.getSeclected()) {
//            setClickBorder(item, view);
//        }
//        if (Setting.longClickFlag && item.getSeclected()) {
//            setLongClickColor(item, view);
//        }
//        else {
//            cardView.setBackgroundColor(Color.WHITE);
//            final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
//            linearLayout.setBackgroundColor(Color.TRANSPARENT);
//        }

        imageButton.setImageDrawable(item.getPicture());
        nameText.setText(item.getName());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TODO: image button click");
            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = item.getName();
//
//                String[] ingredients = new String[]{"butter", "egg", "milk", "american cheese", "cheddar", "sour cream", "yogurt", "cream cheese"};
                myClickListner.clickListner(v, categoryName);


//                if (!Setting.longClickFlag && Setting.count == 0) {
//                    Setting.shortClickFlag = true;
//                }
//
//                Setting.count++;
//                // 单击状态
//                if (Setting.shortClickFlag) {
//                    if (item.getSeclected()) {
//                        cleanBorder(item, view);
//                    } else {
//                        setClickBorder(item, view);
//                    }
//                } else { //长按状态
//                    if (item.getSeclected()) {
//                        cleanBorder(item, view);
//                    } else {
//                        setLongClickColor(item, view);
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
//                    setLongClickColor(item, view);
//                    System.out.println("123");
//                } else {
//                    setClickBorder(item, view);
//                }
//                return true;
//            }
//        });

        return view;
    }


}
