package com.example.ingredieat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import static com.example.ingredieat.Setting.PREF_NAME;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomActivity extends AppCompatActivity {
    private boolean componentReady = false;
    private Intent startIntent = null;

    final TimerTask timerTask = new TimerTask() {
        public void run() {
            synchronized (this) {
                while (!componentReady) try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            WelcomActivity.this.startActivity(startIntent);
            WelcomActivity.this.finish();
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = View.inflate(getApplicationContext(), R.layout.welcome_page, null);
        setContentView(v);
        new Timer().schedule(timerTask, 2000);
        startIntent = new Intent(this, LoginActivity.class);
        componentReady = true;

    }

    // get user account information
    public void retrieveSharedPreferenceInfo() {
        SharedPreferences settings = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    }
}
