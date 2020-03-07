package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button1 = (Button) findViewById(R.id.button1);
        this.button2 = (Button) findViewById(R.id.button2);
        this.linearLayout = (LinearLayout) findViewById(R.id.liner_out);
        this.textView = (TextView) findViewById(R.id.textView);
        this.imageView = new ImageView(this);
        linearLayout.addView(imageView);
        retrieveSharedPreferenceInfo();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.beijing);
                textView.setText(R.string.p1);
                textView.setTextSize(20);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.tiantan);
                textView.setText(R.string.p2);
                textView.setTextSize(20);
            }
        });

    }
    public void saveSharedPreferenceInfo() {
        SharedPreferences info = getSharedPreferences("ActivityInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = info.edit();

        editor.putString("title", textView.getText().toString());
        editor.apply();
    }

    public void retrieveSharedPreferenceInfo() {
        SharedPreferences info = getSharedPreferences("ActivityInfo", Context.MODE_PRIVATE);

        String title = info.getString("title", "!!!Missing");
        System.out.println(title);
        if (title.equals(getString(R.string.p1))) {
            imageView.setImageResource(R.drawable.beijing);
        } else if (title.equals(getString(R.string.p2))) {
            imageView.setImageResource(R.drawable.tiantan);
        }

        textView.setText(title);
        textView.setTextSize(20);
    }

    @Override
    protected void onDestroy() {
        saveSharedPreferenceInfo();
        super.onDestroy();
    }
}
