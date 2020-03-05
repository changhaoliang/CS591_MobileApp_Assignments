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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import java.io.InputStream;



public class GameFragment extends Fragment {
    private final int side = 4;
    private int[] currentIndex;
    private Button currentButton;
    private Button[][] letterButtons;
    private Board board;
    private char[][] letters;
    TextView wordText;
    String word;

    public GameFragment() {
        // Required empty public constructor
    }

    public interface GameFragmentListener {
        public void updateScore(int score);
    }

    GameFragmentListener GFL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        Button clearButton = view.findViewById(R.id.buttonClear);
        Button submitButton = view.findViewById(R.id.buttonSubmit);
        wordText = view.findViewById(R.id.textWord);
        word = wordText.getText().toString();
        board = new Board();
        InputStream input= readDictionary();
        board.setInput(input);
        letters = board.shuffle();
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButtons();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!board.checkLength(word)) {
                    Toast.makeText(getActivity(), "Word length cannot be less than 4!", Toast.LENGTH_LONG).show();
                    clearButtons();
                } else if (!board.checkVowels(word)) {
                    Toast.makeText(getActivity(), "Word must contain at least 2 vowels!", Toast.LENGTH_LONG).show();
                    clearButtons();
                } else if (board.wordRepeated(word)) {
                    Toast.makeText(getActivity(), "Word repeated!", Toast.LENGTH_LONG).show();
                    clearButtons();
                } else {
                    int score = board.updateScore(word);
                    if (board.searchWord(word)) {
                        Toast.makeText(getActivity(), "That's correct! +" + score, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "That's incorrect! " + score, Toast.LENGTH_LONG).show();
                    }
                    clearButtons();
                    GFL.updateScore(score);
                }
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

    private InputStream readDictionary() {
        InputStream input = getResources().openRawResource(R.raw.words);
        return input;
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
                        if (currentButton == null) {
                            setCurrentButton(letterButtons[row][col]);
                            currentIndex = new int[]{row, col};
                        } else {
                            if (!board.isAdjacent(currentIndex, new int[]{row, col})) {
                                Toast.makeText(getActivity(), "Tap on adjacent letters!", Toast.LENGTH_LONG).show();
                                clearButtons();
                            } else {
                                currentButton.getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                                        getResources().getColor(R.color.orange)));
                                setCurrentButton(letterButtons[row][col]);
                                currentIndex = new int[]{row, col};
                            }
                        }
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
        word = "";
        wordText.setText("");
    }

    private void setCurrentButton(Button button) {
        currentButton =button;
        currentButton.getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                getResources().getColor(R.color.red)));
        currentButton.setEnabled(false);
        word = word+button.getText().toString();
        wordText.setText(word);
    }

    public void newGame() {
        clearButtons();
        board = new Board();
        letters = board.shuffle();
        for (int row=0; row<side; row++){
            for (int col=0; col<side; col++){
                letterButtons[row][col].setText(String.valueOf(letters[row][col]));
            }
        }
    }
}
