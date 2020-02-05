package com.example.part2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private Button btn_convert;
    private Button btn_swap;
    private EditText txt_left;
    private EditText txt_right;
    TempConverter tempConverter;
    Boolean fToC = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempConverter = new TempConverter();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_convert = findViewById(R.id.btn_convert);
        btn_swap = findViewById(R.id.btn_swap);
        txt_left = findViewById(R.id.txt_left);
        txt_right = findViewById(R.id.txt_right);

        if(fToC){
            btn_convert.setText("Fahrenheit -> Celsius");
            txt_left.setHint("Fahrenheit");
            txt_right.setHint("Celsius");
        }
        else{
            btn_convert.setText("Celsius -> Fahrenheit");
            txt_left.setHint("Celsius");
            txt_right.setHint("Fahrenheit");
        }

        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(fToC){
                        txt_right.setText(String.valueOf(tempConverter.fToC(Double.valueOf(txt_left.getText().toString()))));
                    }
                    else{
                        txt_right.setText(String.valueOf(tempConverter.cToF(Double.valueOf(txt_left.getText().toString()))));
                    }

                }catch (NumberFormatException e) {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Warning")
                            .setMessage("Please enter a correct number!")
                            .setPositiveButton("Confirm", null)
                            .create();
                    alertDialog1.show();

                    txt_left.setHint("");
                    txt_right.setHint("");
                }
            }
        });
        btn_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fToC){
                    fToC = false;
                }
                else{
                    fToC = true;
                }
                if(fToC){
                    btn_convert.setText("Fahrenheit -> Celsius");
                    txt_left.setHint("Fahrenheit");
                    txt_right.setHint("Celsius");
                }
                else{
                    btn_convert.setText("Celsius -> Fahrenheit");
                    txt_left.setHint("Celsius");
                    txt_right.setHint("Fahrenheit");
                }
            }
        });
    }
}
