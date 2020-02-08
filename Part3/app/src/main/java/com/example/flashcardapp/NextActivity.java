package com.example.flashcardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class NextActivity extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnStart;

    private TextView divisorText;
    private TextView dividendText;
    private TextView messageText;
    private EditText answerText;
    private ArrayList<DivisionProblem> problems;
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
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_LONG).show();


        btnSubmit = (Button)findViewById(R.id.submit_button);
        btnStart = (Button)findViewById(R.id.start_game_btn);

        divisorText = (TextView)findViewById(R.id.divisor_textView);
        dividendText = (TextView)findViewById(R.id.dividend_textView);
        answerText = (EditText)findViewById(R.id.answer_edit);
        messageText = (TextView)findViewById(R.id.messages);
        problems = new ArrayList<DivisionProblem>(10);
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
                    play(problems.get(round));
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
                    game.updateScore(input, problems.get(round));
                    answerText.setText("");
                    round++;
                    if(round < 10) {
                        messageText.setText(String.format(Locale.ENGLISH, message, round + 1, 10, game.getScore()));
                        play(problems.get(round));
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // save all values of parameters
        outState.putString("divisor", divisorText.getText().toString());
        outState.putString("dividend", dividendText.getText().toString());
        outState.putString("message", messageText.getText().toString());
        outState.putString("answer", answerText.getText().toString());
        outState.putInt("round", round);
        outState.putParcelableArrayList("problems", problems);
        outState.putParcelable("game", game);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String divisor = savedInstanceState.getString("divisor");
        String dividend = savedInstanceState.getString("dividend");
        String message = savedInstanceState.getString("message");
        String answer = savedInstanceState.getString("answer");

        divisorText.setText(divisor);
        dividendText.setText(dividend);
        game = savedInstanceState.getParcelable("game");
        int score = game.getScore();
        int round = savedInstanceState.getInt("round");

        messageText.setText(String.format(Locale.ENGLISH, message, round, 10, score));
        answerText.setText(answer);

        problems = savedInstanceState.getParcelableArrayList("problems");
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
