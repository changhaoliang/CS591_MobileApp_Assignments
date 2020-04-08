package com.example.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TempConverter tempConverter;
    TextView textC, textF, valueC, valueF, message;
    SeekBar seekBarC, seekBarF;
    boolean isSeekBarCChanging;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempConverter = new TempConverter();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        textC = (TextView) findViewById(R.id.TextC);
        textF = (TextView) findViewById(R.id.TextF);
        valueC = (TextView) findViewById(R.id.ValueC);
        valueF = (TextView) findViewById(R.id.ValueF);
        message = (TextView) findViewById(R.id.Message);
        seekBarC = (SeekBar) findViewById(R.id.SeekBarC);
        seekBarF = (SeekBar) findViewById(R.id.SeekBarF);
        seekBarC.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (isSeekBarCChanging) {
                    double value = (i - 1777.77) / 100f;
                    valueC.setText(String.format("%.2f", value));
                    double invert = tempConverter.cToF(value);
                    valueF.setText(String.format("%.2f", invert));
                    int invertInt = (int) (invert * 100);
                    seekBarF.setProgress(invertInt);
                }
                showMessage();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarCChanging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarF.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!isSeekBarCChanging) {
                    double value = i / 100f;
                    valueF.setText(String.format("%.2f", value));
                    double invert = tempConverter.fToC(value);
                    valueC.setText(String.format("%.2f", invert));
                    int invertInt = (int) ((invert * 100 + 1778));
                    seekBarC.setProgress(invertInt);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarCChanging = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void showMessage() {
        if (seekBarC.getProgress() < 2978) {
            message.setText(getResources().getString(R.string.message_cold));
        } else {
            message.setText(getResources().getString(R.string.message_not_cold));
        }
    }
}
