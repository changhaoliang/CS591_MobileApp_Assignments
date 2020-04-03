package com.example.ingredieat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private int layoutId;
    private List<Item> items;
    public boolean longClickFlag;
    private MyLongClickListner myLongClickListner;
    public interface MyLongClickListner {
        public void longClickListner(View v);
    }
    public ItemAdapter(Context context, int layoutId, List<Item> list, MyLongClickListner listner){
        super(context,layoutId,list);
        this.layoutId = layoutId;
        this.items = list;
        this.myLongClickListner = listner;
        this.longClickFlag = false;
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

    public void setClickBorder(Item item, View view) {
        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);

        cardView.setBackgroundColor(getContext().getResources().getColor(R.color.card_border));
        item.setSelected(true);
        linearLayout.setBackgroundColor(Color.WHITE);
    }

    public void cleanBorder(Item item, View view) {
        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
        cardView.setBackgroundColor(Color.WHITE);
        item.setSelected(false);
    }

    public void setLongClickColor(Item item, View view) {
        final CardView cardView = (CardView)view.findViewById(R.id.card_view);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);
        cardView.setBackgroundColor(getContext().getResources().getColor(R.color.card_border));
        item.setSelected(true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
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
        ImageButton imageButton = (ImageButton)view.findViewById(R.id.imageButton);
        final TextView nameText = (TextView)view.findViewById(R.id.name_txt);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.card_linear_layout);

        imageButton.setImageDrawable(item.getPicture());
        nameText.setText(item.getName());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("2314124121");
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!longClickFlag) {
                    if (item.getSeclected()) {
                        cleanBorder(item, view);
                    } else {
                        setClickBorder(item, view);
                    }
                }
                else {
                    if (item.getSeclected()) {
                        cleanBorder(item, view);
                    } else {
                        setLongClickColor(item, view);
                    }
                }
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myLongClickListner.longClickListner(v);
                longClickFlag = true;
                setLongClickColor(item, view);
                return true;
            }
        });

        return view;
    }


}
