package com.example.calories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.TextView;
import android.text.TextWatcher;

public class MainActivity extends AppCompatActivity {

    private TextView[] nums;
    private TextView totalCal;
    private final int total = 4;
    private final int[] cals = {390, 570, 250, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nums = new TextView[4];
        nums[0] = (TextView) findViewById(R.id.textNum1);
        nums[1] = (TextView) findViewById(R.id.textNum2);
        nums[2] = (TextView) findViewById(R.id.textNum3);
        nums[3] = (TextView) findViewById(R.id.textNum4);
        totalCal = (TextView) findViewById(R.id.lblTotalCal);

        for (int i = 0; i < total; i++) {
            nums[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    setTotalCal();
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    setTotalCal();
                }
            });
        }
    }

    private void setTotalCal() {
        int sum = 0;
        try {
            for (int i = 0; i < total; i++) {
                if (nums[i].getText().length() > 0)
                    sum += cals[i] * Integer.parseInt(nums[i].getText().toString());
            }
            totalCal.setText(sum + " Cal.");
        } catch (Exception e) {

        }
    }
}
