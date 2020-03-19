package com.example.simplesynthesis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton phoneBtn;
    private String phoneNumber;
    private String textNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneBtn = (ImageButton)findViewById(R.id.phone_imgbtn);

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveSharedPreferenceInfo();
                Intent phoneCall = new Intent(Intent.ACTION_DIAL);
                phoneCall.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(phoneCall);
            }
        });
    }
    public void retrieveSharedPreferenceInfo() {
        SharedPreferences info = getSharedPreferences("PreferenceInfo", Context.MODE_PRIVATE);
        phoneNumber = info.getString("phone", "");
        textNumber = info.getString("text", "");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preference_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.preference_menu_btn) {
            Intent intent = new Intent(getApplicationContext(), PreferenceActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
