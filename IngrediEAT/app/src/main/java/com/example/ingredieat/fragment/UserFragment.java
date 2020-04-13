package com.example.ingredieat.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ingredieat.R;
import com.example.ingredieat.acitivity.HomeActivity;
import com.example.ingredieat.acitivity.LoginActivity;
import com.example.ingredieat.setting.Setting;
import com.google.android.material.button.MaterialButton;


public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_user, container, false);

        MaterialButton button = myView.findViewById(R.id.log_out);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
