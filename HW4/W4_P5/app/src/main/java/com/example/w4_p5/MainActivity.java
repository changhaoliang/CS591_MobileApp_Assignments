package com.example.w4_p5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    private Button[][] letterButtons;
    private final int kbRows = 4, kbColumns = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        RelativeLayout myLayout = findViewById(R.id.root);
        setContentView(myLayout);
        WordInput wordInput = new WordInput(this, 5);
        myLayout.addView(wordInput);
    }

    public void buildKeyboard(){
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
                letterButtons[row][col].setOnClickListener(bh);
            }
        }
        letterButtons[3][0].setVisibility(View.INVISIBLE);
        letterButtons[3][kbColumns-1].setVisibility(View.INVISIBLE);
    }
    private boolean checkLetter(Button b){
        char c = b.getText().charAt(0);
        //if c is the answer return true, and set button invisible
        return false;
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
