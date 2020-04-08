package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        buildGuiByCode();
    }

    public void buildGuiByCode() {
        // rootLayout
        LinearLayout loginLayout = new LinearLayout(this);

        loginLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams loginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loginParams.gravity = Gravity.CENTER_HORIZONTAL;
        loginLayout.setLayoutParams(loginParams);
        setContentView(loginLayout);

        // User account layout, horizontal layout
        LinearLayout userLayout = new LinearLayout(this);
        userLayout.setOrientation(LinearLayout.HORIZONTAL);
        loginLayout.addView(userLayout);

        LinearLayout.LayoutParams userLayoutParams = (LinearLayout.LayoutParams) userLayout.getLayoutParams();
        userLayoutParams.setMargins(200, 400, 200, 200);

        // show user TextView
        TextView userText = new TextView(this);
        userText.setText("User :  ");
        userText.setTextSize(30);
        userLayout.addView(userText);

        // user EditText
        final EditText userEdit = new EditText(this);
        userLayout.addView(userEdit);
        userEdit.setHint("username");
        userEdit.setTextSize(30);
        LinearLayout.LayoutParams edtUserLayoutParams = (LinearLayout.LayoutParams) userEdit.getLayoutParams();
        userEdit.setWidth(0);
        edtUserLayoutParams.weight = 1;

        // password Linear layout
        LinearLayout pwdLayout = new LinearLayout(this);
        pwdLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams pwdLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pwdLayout.setLayoutParams(pwdLayoutParams);
        pwdLayoutParams.setMargins(200, 50, 200, 200);
        loginLayout.addView(pwdLayout);

        // password Textview
        TextView passwordText = new TextView(this);
        passwordText.setText("Pass :  ");
        pwdLayout.addView(passwordText);
        passwordText.setTextSize(30);

        // password EditText
        final EditText pwdEdit = new EditText(this);
        pwdEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        pwdEdit.setHint("password");
        pwdEdit.setTextSize(30);
        pwdLayout.addView(pwdEdit);
        LinearLayout.LayoutParams pwdEditParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pwdEditParams.weight = 1;
        pwdEditParams.width = 0;
        pwdEdit.setLayoutParams(pwdEditParams);

        // Submit button
        Button btnLogin = new Button(this);
        btnLogin.setText("Submit");
        btnLogin.setTextSize(30);
        loginLayout.addView(btnLogin);
        LinearLayout.LayoutParams btnLoginParams = (LinearLayout.LayoutParams) btnLogin.getLayoutParams();

        btnLoginParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        btnLoginParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        btnLoginParams.gravity = Gravity.CENTER_HORIZONTAL;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount ua = new UserAccount("jack", "1111");

                String username = userEdit.getText().toString();
                String password = pwdEdit.getText().toString();
                if (ua.checkValidAccount(username, password)) {
                    System.out.println("11111");
                    Intent intent = new Intent(MainActivity.this, NextActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        });
    }

}
