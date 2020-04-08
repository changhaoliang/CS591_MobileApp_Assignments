package com.example.flingingmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector;
    private CurrencyConverter converter;
    private TextView Euro, Dollar, Yen, Yuan, Pound;
    private final int minDistance = 10, minVelocity = 10;   //threshold for fling&scroll

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        converter = new CurrencyConverter();
        buildGui();
        gestureDetector = new GestureDetector(this, this);
    }

    private void buildGui() {
        Euro = (TextView) findViewById(R.id.editEuro);
        Dollar = (TextView) findViewById(R.id.editDollar);
        Yen = (TextView) findViewById(R.id.editYen);
        Yuan = (TextView) findViewById(R.id.editYuan);
        Pound = (TextView) findViewById(R.id.editPound);
        Dollar.setFocusable(false); //set uneditable
        Yen.setFocusable(false);
        Yuan.setFocusable(false);
        Pound.setFocusable(false);

        Euro.addTextChangedListener(new TextWatcher() {  //update currencies while input
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Euro.getText().length() > 0) {
                    converter.setCurrency(Double.valueOf(Euro.getText().toString()));
                    updateCurrencies();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Euro.getText().length() > 0) {
                    converter.setCurrency(Double.valueOf(Euro.getText().toString()));
                    updateCurrencies();
                } else if (Euro.getText().length() == 0) {
                    Dollar.setText("");
                    Yen.setText("");
                    Yuan.setText("");
                    Pound.setText("");
                }
            }
        });
    }

    private void updateCurrencies() {
        Dollar.setText(converter.getDollar());
        Yen.setText(converter.getYen());
        Yuan.setText(converter.getYuan());
        Pound.setText(converter.getPound());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (Euro.getText().length() == 0) return true;
        if (distanceY > minDistance) {   //upwards
            converter.addCent();
            Euro.setText(converter.getEuro());
            updateCurrencies();
        } else if (distanceY < -minDistance) { //downwards
            converter.minCent();
            Euro.setText(converter.getEuro());
            updateCurrencies();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Euro.getText().length() == 0) return true;
        if (velocityY < -minVelocity) {  //upwards
            converter.addDollar();
            Euro.setText(converter.getEuro());
            updateCurrencies();
        } else if (velocityY > minVelocity) {     //downwards
            converter.minDollar();
            Euro.setText(converter.getEuro());
            updateCurrencies();
        }
        return true;
    }
}
