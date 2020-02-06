package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnStart;

    private TextView dividerText;
    private TextView divedentText;
    private TextView messageText;

    private EditText answerText;
    DivisionProblem[] problems = new DivisionProblem[10];
    private DivisionProblem currentProblem;

    DivisionGame game = new DivisionGame();
    boolean ifSubmit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        btnSubmit = (Button)findViewById(R.id.submit_button);
        btnStart = (Button)findViewById(R.id.start_game_btn);

        dividerText = (TextView)findViewById(R.id.divider_textView);
        divedentText = (TextView)findViewById(R.id.dividend_textview);
        answerText = (EditText)findViewById(R.id.answer_edit);
        messageText = (TextView)findViewById(R.id.messages);
        currentProblem = new DivisionProblem();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problems = game.playGame(10);
                play(problems);
            }
        });
    }

    public void play(DivisionProblem[] problems) {
        System.out.println("==========================");
        //////////////////////////////////////////////
        ////////////!!!!!!!!!!!!!!!!!!!!不会写！！！！！！！！！！！！！！！！！！！！！！
        ////////////////////////////////////////////////
        for (int i = 0; i < 1; i ++) {
            dividerText.setText(String.valueOf(problems[i].getDivider()));
            divedentText.setText(String.valueOf(problems[i].getDividend()));
            //ifSubmit = false;

            currentProblem = problems[i];
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = answerText.getText().toString();
                    game.updateScore(input, currentProblem);
                    messageText.setText("Your score: " + game.getScore());
                    //ifSubmit = true;
                }
            });


        }
    }
}
