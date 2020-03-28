package com.example.simplesynthesis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ImageButton phoneBtn;
    private ImageButton textBtn;
    private String phoneNumber;
    private String textNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS} , 1);

        phoneBtn = (ImageButton) findViewById(R.id.phone_imgbtn);
        textBtn = (ImageButton) findViewById(R.id.text_imgbtn);

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveSharedPreferenceInfo();
                Intent phoneCall = new Intent(Intent.ACTION_DIAL);
                phoneCall.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(phoneCall);
            }
        });
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveSharedPreferenceInfo();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(textNumber, null, "**THIS IS ONLY A TEST**", null, null);
                Intent text = new Intent(Intent.ACTION_SENDTO);
                startActivity(text);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
