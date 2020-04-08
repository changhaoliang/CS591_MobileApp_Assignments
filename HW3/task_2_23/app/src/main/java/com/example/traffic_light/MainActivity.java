package com.example.traffic_light;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
//import android.os.Bundle;
//import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {
    Button btnTrafficLight;
    TextView lblTrafficLight;
    long timeout = Long.MAX_VALUE;
    int current = 0;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTrafficLight = (Button) findViewById(R.id.btnTrafficLight);
        lblTrafficLight = (TextView) findViewById(R.id.lblTrafficLight);
        lblTrafficLight.setBackgroundColor(Color.WHITE);


    }

    public void changeColor(View view) {
        final int[] colors = {Color.GREEN, Color.YELLOW, Color.RED};

        lblTrafficLight.setBackgroundColor(colors[current++]);
        if (current == 3)
            current = 0;
        CountDownTimer ctd = new CountDownTimer(timeout, 3000) {

            //int current = 0;

            @Override
            public void onTick(long arg0) {
                Log.d("TEST", "Current color index: " + current);
                lblTrafficLight.setBackgroundColor(colors[current++]);
                if (current == 3)
                    current = 0;
            }

            @Override
            public void onFinish() {

            }
        };
        if (first == true) {
            ctd.start();
            first = false;
        }
    }
}
