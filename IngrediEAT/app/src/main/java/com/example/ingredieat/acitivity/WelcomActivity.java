package com.example.ingredieat.acitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.ingredieat.R;
import com.example.ingredieat.setting.Setting;

import static com.example.ingredieat.setting.Setting.PREF_NAME;

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
        View v = View.inflate(getApplicationContext(), R.layout.activity_welcome, null);
        setContentView(v);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        new Timer().schedule(timerTask, 2000);
        retrieveSharedPreferenceInfo();
        System.out.println(Setting.ifSignIn);

        if (!Setting.ifSignIn) {
            startIntent = new Intent(this, LoginActivity.class);
        }
        else {
            startIntent = new Intent(this, LibraryActivity.class);
        }
        componentReady = true;

    }

    // get user account information
    public void retrieveSharedPreferenceInfo() {
        SharedPreferences settings = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Setting.email = settings.getString(Setting.Strings.account_email, null);
        Setting.familyName = settings.getString(Setting.Strings.account_family_name, null);
        Setting.givenName = settings.getString(Setting.Strings.account_given_name, null);
        Setting.googleId = settings.getString(Setting.Strings.account_id, null);
        Setting.ifSignIn = settings.getBoolean(Setting.Strings.if_signin_succ, false);
    }
}
