package com.example.w4_p4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Home extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private GestureDetector gestureDetector;
    private int minDistance = 200;
    private int minVelocity = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, this);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // right fling
        if (e2.getX() - e1.getX() > minDistance && Math.abs(velocityX) > minVelocity) {
            Intent intent = new Intent(Home.this, East.class);
            startActivity(intent);
        }
        // left fling
        else if (e1.getX() - e2.getX() > minDistance && Math.abs(velocityX) > minVelocity) {
            Intent intent = new Intent(Home.this, West.class);
            startActivity(intent);
        }
        // down fling
        else if (e2.getY() - e1.getY() > minDistance && Math.abs(velocityY) > minVelocity) {
            System.out.println(e2.getY());
            System.out.println(e1.getY());
            Intent intent = new Intent(Home.this, South.class);
            startActivity(intent);
        }
        // up fling
        else if (e1.getY() - e2.getY() > minDistance && Math.abs(velocityY) > minVelocity) {
            Intent intent = new Intent(Home.this, North.class);
            startActivity(intent);
        }
        return false;
    }
}
