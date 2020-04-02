package com.example.ingredieat;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LibraryActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        linearLayout = (LinearLayout)findViewById(R.id.linear_layout);
        context = getApplicationContext();

        addCardView("Pork Belly", getResources().getDrawable(R.drawable.pork, null));

    }


    public void addCardView(String name, Drawable picture){

        CardView cardview = new CardView(getApplicationContext());

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        cardview.setLayoutParams(layoutparams);
        cardview.setRadius(40);
        cardview.setPadding(25, 25, 25, 25);
        cardview.setClickable(true);
        cardview.setCardElevation(30); // shadow

        LinearLayout cardLayout = new LinearLayout(cardview.getContext());
        layoutparams.setMargins(10, 20, 10,20);
        cardLayout.setLayoutParams(layoutparams);

        ImageButton imageButton = new ImageButton(context);
        imageButton.setImageDrawable(picture);
        cardLayout.addView(imageButton);

        TextView textview = new TextView(context);
        textview.setLayoutParams(layoutparams);
        textview.setText(name);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor(Color.BLACK);
        textview.setPadding(50,25,50,25);
        textview.setGravity(Gravity.CENTER);
        cardLayout.addView(textview);

        cardview.addView(cardLayout);

        linearLayout.addView(cardview);

    }
}
