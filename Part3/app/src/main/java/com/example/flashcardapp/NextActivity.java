package com.example.flashcardapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class NextActivity extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnStart;

    private TextView divisorText;
    private TextView dividendText;
    private TextView messageText;
    private EditText answerText;
    DivisionProblem[] problems;
    private int round;

    DivisionGame game;
    boolean isPlaying;
    boolean ifSubmit;

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_next);

        btnSubmit = (Button)findViewById(R.id.submit_button);
        btnStart = (Button)findViewById(R.id.start_game_btn);

        divisorText = (TextView)findViewById(R.id.divisor_textView);
        dividendText = (TextView)findViewById(R.id.dividend_textView);
        answerText = (EditText)findViewById(R.id.answer_edit);
        messageText = (TextView)findViewById(R.id.messages);
        problems = new DivisionProblem[10];
        game = new DivisionGame();
        isPlaying = false;
        message = "Round: %d/%d     Your score: %d.";

        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPlaying) {
                    isPlaying = true;
                    round = 0;
                    problems = game.playGame(10);
                    messageText.setText(String.format(Locale.ENGLISH, message, round + 1, 10, game.getScore()));
                    play(problems[round]);
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(NextActivity.this)
                            .setTitle("Warning")
                            .setMessage("Game in progress, you cannot click this button now.")
                            .setPositiveButton("Confirm", null)
                            .create();
                    alertDialog.show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = answerText.getText().toString();
                try {
                    int input = Integer.valueOf(inputString);
                    game.updateScore(input, problems[round]);
                    answerText.setText("");
                    round++;
                    if(round < 10) {
                        messageText.setText(String.format(Locale.ENGLISH, message, round + 1, 10, game.getScore()));
                        play(problems[round]);
                    } else{
                        AlertDialog alertDialog = new AlertDialog.Builder(NextActivity.this)
                                .setTitle("Information")
                                .setMessage(String.format("Game over! Your score is %d.", game.getScore()))
                                .setPositiveButton("Confirm", null)
                                .create();
                        alertDialog.show();
                        reset();
                    }
                }catch (NumberFormatException e) {
                    AlertDialog alertDialog = new AlertDialog.Builder(NextActivity.this)
                            .setTitle("Warning")
                            .setMessage("Please enter a correct number!")
                            .setPositiveButton("Confirm", null)
                            .create();
                    alertDialog.show();
                }

                //ifSubmit = true;
            }
        });
    }



    public void reset() {
        isPlaying = false;
        game.resetGame();
        divisorText.setText("number");
        dividendText.setText("number");
        messageText.setText("message");
    }

    public void play(DivisionProblem problem) {
        divisorText.setText(String.valueOf(problem.getDivider()));
        dividendText.setText(String.valueOf(problem.getDividend()));
    }
}
