package com.example.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBarF;
    private SeekBar seekBarC;

    private TextView cTemperature;
    private TextView fTemperature;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBarC = (SeekBar)findViewById(R.id.c_seekBar);
        seekBarF = (SeekBar)findViewById(R.id.f_seekBar);

        cTemperature = (TextView)findViewById(R.id.c_temp_textview);
        fTemperature = (TextView)findViewById(R.id.f_temp_textview);
        message = (TextView)findViewById(R.id.message_textview);

        seekBarC.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cTemperature.setText(Integer.toString(progress));
                System.out.println(progress);
                fTemperature.setText(Integer.toString(progress + 32));
                seekBarF.setProgress(progress + 32);
                if (progress < -12) {
                    message.setText("it's cold");
                }
                else {
                    message.setText("it's not cold");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "Start！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "Stop！");
            }
        });

        seekBarF.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fTemperature.setText(Integer.toString(progress));
                cTemperature.setText(Integer.toString(progress - 32));
                seekBarC.setProgress(progress - 32);
                if (progress < -12 + 32) {
                    message.setText("it's cold");
                }
                else {
                    message.setText("it's not cold");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "Start！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "Stop！");
            }
        });
    }
}
