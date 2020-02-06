package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NextActivity extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnStart;

    private TextView dividerText;
    private TextView divedentText;
    private TextView messageText;
    private EditText answerText;
    DivisionProblem[] problems;
    private int round;

    DivisionGame game;
    boolean playing;
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
        problems = new DivisionProblem[10];
        game = new DivisionGame();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round = 0;
                problems = game.playGame(10);
                play(problems[round]);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = answerText.getText().toString();
                game.updateScore(input, problems[round]);
                messageText.setText("Your score: " + game.getScore());
                answerText.setText("");
                round++;
                play(problems[round]);
                //ifSubmit = true;
            }
        });
    }

    public void play(DivisionProblem problem) {
        dividerText.setText(String.valueOf(problem.getDivider()));
        divedentText.setText(String.valueOf(problem.getDividend()));
    }
}
