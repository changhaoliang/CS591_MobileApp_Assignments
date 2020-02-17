package com.example.w4_p5;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.LightingColorFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button[][] letterButtons;
    private final int kbRows = 4, kbColumns = 7;
    private WordInput wordInput;
    private Hangman hangman;

    private View[] bodyParts;
    private int failTime;
    private int orientation;

    private Button hintButton;
    private TextView hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.orientation = getResources().getConfiguration().orientation;

        bodyParts = new View[8];
        bodyParts[0] = findViewById(R.id.part0);
        bodyParts[1] = findViewById(R.id.part1);
        bodyParts[2] = findViewById(R.id.part2);
        bodyParts[3] = findViewById(R.id.part3);
        bodyParts[4] = findViewById(R.id.part4);
        bodyParts[5] = findViewById(R.id.part5);
        bodyParts[6] = findViewById(R.id.part6);
        bodyParts[7] = findViewById(R.id.part7);

        Button startButton = (Button)findViewById(R.id.button);
        if (orientation == getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            hintButton = (Button) findViewById(R.id.hintButton);
            hint = (TextView) findViewById(R.id.hintString);
            hintButton.setVisibility(View.INVISIBLE);
            hintButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hint.setText(hangman.getRound().getHint());
                    hintButton.setEnabled(false);
                }
            });
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordInput != null) {
                    wordInput.clear();
                }

                failTime = 0;

                hangman = new Hangman();
                hangman.startGame();

                init();
            }
        });

        startButton.getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                0X007EC0EE));

    }

    public void init() {
        wordInput = new WordInput(this, hangman.getRound().getWord().length());
        RelativeLayout.LayoutParams wordInputLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wordInputLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        wordInputLayoutParams.addRule(RelativeLayout.BELOW, R.id.hangman);

        GridLayout keyBoard = buildKeyboard();
        RelativeLayout.LayoutParams keyBoardLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        keyBoardLayoutParams.topMargin = 300;

        if (orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            RelativeLayout myLayout = findViewById(R.id.root);
            myLayout.addView(wordInput, wordInputLayoutParams);
            keyBoardLayoutParams.addRule(RelativeLayout.BELOW, R.id.hangman);
            myLayout.addView(keyBoard, keyBoardLayoutParams);
        } else if (orientation == getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            hintButton.setEnabled(true);
            hintButton.setVisibility(View.VISIBLE);
            hint.setText("");
            RelativeLayout panelRight = findViewById(R.id.panelRight);
            RelativeLayout panel = findViewById(R.id.panelLeft);
            keyBoardLayoutParams.leftMargin = 140;
            keyBoardLayoutParams.topMargin = 260;
            panelRight.addView(wordInput, wordInputLayoutParams);
            panel.addView(keyBoard, keyBoardLayoutParams);
        }

        for (int i = 0; i < 8; i++) {
            bodyParts[i].setVisibility(View.INVISIBLE);
        }
    }


    public GridLayout buildKeyboard(){
        Point size = new Point();
        int w = 0;
        getWindowManager().getDefaultDisplay().getSize(size);
        if (orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT) {
            w = size.x / kbColumns;
        } else if (orientation == getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            w = (int)(size.y / kbColumns * 0.8);
        }
        GridLayout keyBoard = new GridLayout(this);
        keyBoard.setColumnCount(kbColumns);
        keyBoard.setRowCount(kbRows);

        letterButtons = new Button[kbRows][kbColumns];
        ButtonHandler bh = new ButtonHandler();
        for (int row=0; row<kbRows; row++){
            for (int col=0; col<kbColumns; col++){
                letterButtons[row][col] = new Button(this);
                int letter = row==3?(64+row*7+col):(65+row*7+col);
                letterButtons[row][col].setText((char)letter+"");
                letterButtons[row][col].setTextSize(20);
                letterButtons[row][col].setOnClickListener(bh);
                //letterButtons[row][col].setBackgroundColor(getResources().getColor(R.color.white));
                letterButtons[row][col].getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                        0X00FFFFFF));
                keyBoard.addView(letterButtons[row][col], w, w);
            }
        }
        letterButtons[3][0].setVisibility(View.INVISIBLE);
        letterButtons[3][kbColumns-1].setVisibility(View.INVISIBLE);
        return keyBoard;
    }

    private void checkLetter(Button b){
        char c = Character.toLowerCase(b.getText().charAt(0));
        //if c is the answer return true, and set button invisible
        if (hangman.getRound().isCharGuessed(c)) {
            // set style of button
            b.setEnabled(false);
            b.setTextColor(getResources().getColor(R.color.white));
            //b.setBackgroundColor(getResources().getColor(R.color.green));
            b.getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                    0X0043CD80));
            ArrayList<Integer> position = hangman.getRound().getPosition(c);

            // set content of EditText
            for (int i : position) {
                wordInput.setText(c, i);
            }

            // update score
            hangman.getRound().updateScore(c);

            // check if the word is guessed
            if (hangman.getRound().isWordGuessed()) {
                Toast.makeText(getApplicationContext(), "Total Score: " + String.valueOf(hangman.getRound().getScore()), Toast.LENGTH_LONG).show();
                // should reset
                disableAllButton();
            }
        } else {
            // draw hangman !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            bodyParts[failTime].setVisibility(View.VISIBLE);
            failTime++;
            b.setEnabled(false);
            b.setTextColor(getResources().getColor(R.color.white));
            //b.setBackgroundColor(getResources().getColor(R.color.red));
            b.getBackground().setColorFilter(new LightingColorFilter(0x00000000,
                    0X00EE3B3B));

            if (failTime == 8) {
                Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();
                disableAllButton();
            }
        }
    }
    public void disableAllButton() {
        for (int i = 0; i < letterButtons.length; i++) {
            for (int j = 0; j < letterButtons[i].length; j++) {
                letterButtons[i][j].setEnabled(false);
            }
        }
    }
    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v){
            for (int row=0; row<kbRows; row++){
                for (int col=0; col<kbColumns; col++){
                    if (v == letterButtons[row][col]){
                        checkLetter(letterButtons[row][col]);
                    }
                }
            }
        }
    }

}
