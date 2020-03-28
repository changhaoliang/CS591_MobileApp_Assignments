package com.example.simplesynthesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferenceActivity extends AppCompatActivity {
    private EditText phoneContactNumber;
    private EditText phoneContactName;
    private EditText textContactNumber;
    private EditText textContactName;

    private Button clearButton;
    private Button saveButton;

    private String phoneNumber;
    private String textNumber;

    private String phoneName;
    private String textName;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        clearButton = (Button)findViewById(R.id.clear_btn);
        saveButton = (Button)findViewById(R.id.save_btn);

        phoneContactName = (EditText)findViewById(R.id.phone_edt_name);
        phoneContactNumber = (EditText)findViewById(R.id.phone_edt_number);
        textContactName = (EditText)findViewById(R.id.txt_edt_name);
        textContactNumber = (EditText)findViewById(R.id.txt_edt_number);
        retrieveSharedPreferenceInfo();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = phoneContactNumber.getText().toString();
                textNumber = textContactNumber.getText().toString();

                phoneName = phoneContactName.getText().toString();
                textName = textContactName.getText().toString();

                savedSharedPreferenceInfo();

                Intent intent = new Intent(PreferenceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneContactName.setText("");
                phoneContactNumber.setText("");
                textContactName.setText("");
                textContactNumber.setText("");
            }
        });
    }

    public void savedSharedPreferenceInfo() {
        SharedPreferences info = getSharedPreferences("PreferenceInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = info.edit();

        editor.putString("phone", phoneNumber);
        editor.putString("text", textNumber);

        editor.putString("phonename", phoneName);
        editor.putString("textname", textName);

        editor.apply();
        System.out.println("---------------Saved---------------");
    }

    public void retrieveSharedPreferenceInfo() {
        SharedPreferences info = getSharedPreferences("PreferenceInfo", Context.MODE_PRIVATE);
        phoneNumber = info.getString("phone", "");
        textNumber = info.getString("text", "");
        phoneName = info.getString("phonename", "");
        textName = info.getString("textname", "");

        phoneContactName.setText(phoneName);
        phoneContactNumber.setText(phoneNumber);

        textContactNumber.setText(textNumber);
        textContactName.setText(textName);

    }

    @Override
    protected void onResume() {
        retrieveSharedPreferenceInfo();
        super.onResume();
    }
}
