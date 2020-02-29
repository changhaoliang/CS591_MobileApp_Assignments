package com.example.w5_p3;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BottomFragment extends Fragment {

    int totalScore;
    TextView scoreView;

    public BottomFragment() {
        // Required empty public constructor
    }

    public interface BottomFragmentListener {
        public void newGame();
    }

    BottomFragmentListener BFL;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        BFL = (BottomFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);   //before returning the inflated view.
        scoreView = view.findViewById(R.id.textScore2);
        Button newGameButton = view.findViewById(R.id.button);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "New game starts!", Toast.LENGTH_LONG).show();
                totalScore = 0;
                scoreView.setText("0");
                BFL.newGame();
            }
        });
        totalScore = 0;
        return view;
    }

    public void updateScore(int score) {
        totalScore += score;
        scoreView.setText(String.valueOf(totalScore));
    }
}
