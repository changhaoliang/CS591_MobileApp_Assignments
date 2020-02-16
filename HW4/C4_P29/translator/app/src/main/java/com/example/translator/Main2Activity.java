package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private Button btnBack;
    private TextView txtTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnBack = (Button) findViewById(R.id.btnBack);
        txtTrans = (TextView) findViewById(R.id.txtTrans);

        Bundle bundle = getIntent().getExtras();
        String language = bundle.getString("language");

        switch(language){
            case "Chinese":
                txtTrans.setText("你好世界");
                break;
            case "Japanese":
                txtTrans.setText("こんにちは世界");
                break;
            case "Spanish":
                txtTrans.setText("Hola Mundo");
                break;
            case "Korean":
                txtTrans.setText("안녕 세상");
                break;
            case "French":
                txtTrans.setText("Bonjour le monde");
                break;
            default:
                txtTrans.setText("Hello World");
        }


        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }

        });


    }
}
