package com.example.ingredieat.acitivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.ingredieat.R;
import com.example.ingredieat.entity.User;
import com.example.ingredieat.setting.Setting;
import com.example.ingredieat.utils.HttpUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Set;

public class LoginActivity extends BaseActivity {
    private static final int RC_SIGN_IN = 0;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    //private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Setting.ifSignIn) { // Check whether there exists a signed-in account.
            Intent startIntent = new Intent(this, HomeActivity.class);
            LoginActivity.this.startActivity(startIntent);
        }else {
            setContentView(R.layout.activity_login);
            SignInButton signInButton = findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            //account = GoogleSignIn.getLastSignedInAccount(this);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            SharedPreferences settings = getSharedPreferences(Setting.PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(Setting.Strings.account_id, account.getId());
            editor.putString(Setting.Strings.account_email, account.getEmail());
            editor.putString(Setting.Strings.account_family_name, account.getFamilyName());
            editor.putString(Setting.Strings.account_given_name, account.getGivenName());
            editor.putBoolean(Setting.Strings.if_signin_succ, true);
            editor.apply();

            User user = new User(Setting.googleId, Setting.email, Setting.givenName, Setting.familyName);

            String jsonString = JSON.toJSONString(user);
            Log.d(TAG, jsonString);

            HttpUtils.postRequest("/login/findOrAddUser", jsonString, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "onFailure -- >" + e.toString());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    Log.d(TAG, "Successfully Sign In");
                    finish();
                }
            });
        } catch (ApiException e) {
            e.printStackTrace();
            Log.d(TAG, "Log in Failed");
        }
    }


}
