package com.example.w5_p3;

import android.graphics.LightingColorFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;


public class GameFragment extends Fragment {
    private final int side = 4;
    private int[] currentIndex;
    private Button currentButton;
    private Button[][] letterButtons;
    private char[][] letters = {{'a', 'b', 'c', 'd'}, {'a', 'b', 'c', 'd'}, {'a', 'b', 'c', 'd'}, {'a', 'b', 'c', 'd'}};

    public GameFragment() {
        // Required empty public constructor
    }

    public interface GameFragmentListener {
        public void sendMessage(String msg, String msg2);
    }

    GameFragmentListener GFL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        Button clearButton = view.findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButtons();
            }
        });
        currentIndex = new int[2];
        createLetterBoard(view);
        return view;
    }

    private void createLetterBoard(View view){
        GridLayout gridLayout = (GridLayout) view.findViewById(R.id.letterBoard);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / side;
        gridLayout.setRowCount(side);
        gridLayout.setColumnCount(side);

        letterButtons = new Button[side][side];
        ButtonHandler bh = new ButtonHandler();


        for (int row=0; row<side; row++){
            for (int col=0; col<side; col++){
                letterButtons[row][col] = new Button(getActivity());
                letterButtons[row][col].setText(String.valueOf(letters[row][col]));
                letterButtons[row][col].setTextSize(25);
                letterButtons[row][col].setOnClickListener(bh);
                letterButtons[row][col].getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                        0X00FFFFFF));

                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(w-40, w-40);
                GridLayout.LayoutParams gl = new GridLayout.LayoutParams(ll);
                gl.leftMargin = 20;
                gl.rightMargin = 20;
                gl.topMargin = 20;
                gl.bottomMargin = 20;
                letterButtons[row][col].setLayoutParams(gl);

                gridLayout.addView(letterButtons[row][col]);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        GFL = (GameFragmentListener) activity;
    }

    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            for (int row=0; row<side; row++){
                for (int col=0; col<side; col++){
                    if (v == letterButtons[row][col]){
                        if (currentButton != null) {
                            currentButton.getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                                    getResources().getColor(R.color.orange)));
                        }
                        currentIndex[0] = row;
                        currentIndex[1] = col;
                        currentButton = letterButtons[row][col];
                        currentButton.getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                                getResources().getColor(R.color.red)));
                        currentButton.setEnabled(false);
                    }
                }
            }
        }
    }

    private void clearButtons() {
        for (int row=0; row<side; row++){
            for (int col=0; col<side; col++){
                letterButtons[row][col].setEnabled(true);
                letterButtons[row][col].getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                        0X00FFFFFF));
                currentButton = null;
                currentIndex = new int[2];
            }
        }
    }

    private void checkLetter(Button button){

    }

    private void setColor(Button button){

    }
}
