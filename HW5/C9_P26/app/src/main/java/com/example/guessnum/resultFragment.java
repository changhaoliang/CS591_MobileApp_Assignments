package com.example.guessnum;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;


public class resultFragment extends Fragment {

    private TextView txtResult;


    public resultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        txtResult = (TextView) v.findViewById(R.id.txtResult);
        return v;
    }


    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void checkGuessNum(int guessNum) {
        Random random = new Random();
        if (random.nextInt(5) + 1 == guessNum) {
            txtResult.setText("You Win!");
        } else {
            txtResult.setText("You lost!");
        }

    }
}
