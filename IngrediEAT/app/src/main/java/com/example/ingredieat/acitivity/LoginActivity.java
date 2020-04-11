package com.example.ingredieat.acitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.ingredieat.R;
import com.example.ingredieat.setting.Setting;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends BaseActivity {
    private static final int RC_SIGN_IN = 0;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Setting.ifSignIn) {
            Intent startIntent = new Intent(this, HomeActivity.class);
            LoginActivity.this.startActivity(startIntent);
        }else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            SignInButton signInButton = findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);


            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            account = GoogleSignIn.getLastSignedInAccount(this);
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
            editor.putString(Setting.Strings.account_email, account.getEmail());
            editor.putString(Setting.Strings.account_family_name, account.getFamilyName());
            editor.putString(Setting.Strings.account_given_name, account.getGivenName());
            editor.putString(Setting.Strings.account_id, account.getId());
            editor.putBoolean(Setting.Strings.if_signin_succ, true);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            System.out.println("Successfully Log in");
            finish();
        } catch (ApiException e) {
            e.printStackTrace();
            System.out.println("Log in Failed");
        }
    }


}
