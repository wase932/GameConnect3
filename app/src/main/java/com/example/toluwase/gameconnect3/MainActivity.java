package com.example.toluwase.gameconnect3;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private float touchX;
    private float touchY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardTouch(findViewById(R.id.gameBoard), findViewById(R.id.red));
    }

    protected void boardTouch(final View board, final View disc) {
        board.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent ev) {
                        touchX = ev.getX() - disc.getWidth() /2;
                        touchY = ev.getY() - disc.getHeight() /2;
                        disc.animate().translationX(touchX).setDuration(1000).start();
                        disc.animate().translationY(touchY).setDuration(1000).start();
                        return true;
                    }
                }
        );
    }
}



