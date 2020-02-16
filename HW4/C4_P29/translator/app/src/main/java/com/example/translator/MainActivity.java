package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Spinner the drop down box
    private Spinner spinner;
    private TextView textView;
    private Button btnTrans;


    private List<String> dataList;

    private ArrayAdapter<String> adapter;

    private String language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
       //textView = (TextView) findViewById(R.id.tv);
        btnTrans = (Button) findViewById(R.id.btnTrans);

        dataList = new ArrayList<String>();
        dataList.add("Chinese");
        dataList.add("Spanish");
        dataList.add("French");
        dataList.add("Japanese");
        dataList.add("Korean");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // textView.setText(adapter.getItem(position));
                language = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //textView.setText("Please Choose Language");
            }
        });

        btnTrans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("language",language );
                startActivity(intent);


            }
        });
    }


}
