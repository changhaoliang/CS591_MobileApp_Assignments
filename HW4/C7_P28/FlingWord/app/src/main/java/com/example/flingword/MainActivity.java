package com.example.flingword;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener{

    private TextView txtMessage;
    private GestureDetector GD;
    private FrameLayout.LayoutParams param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtMessage = (TextView) findViewById(R.id.txtMessage);

        GD = new GestureDetector(this, this);   //Context, Listener as per Constructor Doc.
        //GD.setOnDoubleTapListener(this);   //DoubleTaps implemented a bit differently, must be bound like this.
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);               //Our GD will not automatically receive Android Framework Touch notifications.
        // Insert this line to consume the touch event locally by our GD,
        // IF YOU DON'T insert this before the return, our GD will not receive the event, and therefore won't do anything.
        return super.onTouchEvent(event);          // Do this last, why?
    }

    @Override
    public void onShowPress(MotionEvent e) {
        txtMessage.setText("onShowPress");
    }

    @Override
    public boolean onDown(MotionEvent e) {
        txtMessage.setText("onDown");
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        txtMessage.setText("onFling");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;


        Random r = new Random();

        param = (FrameLayout.LayoutParams) txtMessage.getLayoutParams();

        param.leftMargin=  r.nextInt(width ) ;
        param.topMargin =  r.nextInt(height );
        txtMessage.setLayoutParams(param);

        return true;
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        txtMessage.setText("onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {

        txtMessage.setText("onScroll");
        return true;
    }
    @Override
    public void onLongPress(MotionEvent e) {
        txtMessage.setText("onLongPress");
    }
}
