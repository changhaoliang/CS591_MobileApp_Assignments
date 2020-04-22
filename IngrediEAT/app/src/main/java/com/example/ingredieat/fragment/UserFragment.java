package com.example.ingredieat.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ingredieat.R;
import com.example.ingredieat.acitivity.HomeActivity;
import com.example.ingredieat.acitivity.LoginActivity;
import com.example.ingredieat.setting.Setting;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;


public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }

    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_user, container, false);
        TextView email = myView.findViewById(R.id.user_email);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personEmail = acct.getEmail();
            email.setText(personEmail);
        }
        MaterialButton button = myView.findViewById(R.id.log_out);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Setting.currentMenu = -1;
                SharedPreferences settings = getActivity().getSharedPreferences(Setting.PREF_NAME, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(Setting.Strings.if_signin_succ, false);
                editor.apply();
                Setting.ifSignIn = false;

                Intent startIntent = new Intent(getContext(), LoginActivity.class);
                getActivity().startActivity(startIntent);
                getActivity().finish();
            }
        });

        return myView;
    }

}
