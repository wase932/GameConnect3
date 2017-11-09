package com.example.toluwase.gameconnect3;

import android.os.*;
import android.support.constraint.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private boolean IsFirstPlayer = true;
    Game game = new Game(3);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onScreenTouch();
    }

    public void getClickedView(View view){
        Log.i("ClickedView", "view : " + view.getId());
        int [] location = new int [2];
        view.getLocationInWindow(location);
        int[] boardCoord = convertLocationToBoardCoord(location);
        int animationDestinationX = location[0] - (view.getWidth()/16); // 16: Magic number to position piece
        int animationDestinationY = location[1] - (view.getHeight()/4);
        moveObjectToCoordinates(new int[]{animationDestinationX, animationDestinationY});

//        view.setBackgroundResource(IsFirstPlayer? R.drawable.red: R.drawable.yellow);

        Log.i("ClickedView", "Location Is : " + location[0] +", " + location[1]);
        Log.i("ClickedView", "boardLocation Is : " + boardCoord[0] +", " + boardCoord[1]);

        //playGame
        Game.State result = game.Move(boardCoord[0], boardCoord[1], IsFirstPlayer ? Game.State.X : Game.State.O);

        IsFirstPlayer = !IsFirstPlayer;

        if(result == Game.State.Blank){
            Toast.makeText(getApplicationContext(),"Draw!", Toast.LENGTH_LONG).show();
        }
        if(result == Game.State.X){
            Toast.makeText(getApplicationContext(),"Red Wins!", Toast.LENGTH_LONG).show();
        }
        if(result == Game.State.O){
            Toast.makeText(getApplicationContext(),"Yellow Wins!", Toast.LENGTH_LONG).show();
        }



        //onClick();
    }

    private int[] convertLocationToBoardCoord(int[] location) {
        if (location[0] == 0 && location[1] == 210){
            return new int[] {0,0};
        }
        if (location[0] == 360 && location[1] == 210){
            return new int[] {0,1};
        }
        if (location[0] == 720 && location[1] == 210){
            return new int[] {0,2};
        }
        if (location[0] == 0 && location[1] == 738){
            return new int[] {1,0};
        }
        if (location[0] == 360 && location[1] == 738){
            return new int[] {1,1};
        }
        if (location[0] == 720 && location[1] == 738){
            return new int[] {1,2};
        }
        if (location[0] == 0 && location[1] == 1266){
            return new int[] {2,0};
        }
        if (location[0] == 360 && location[1] == 1266){
            return new int[] {2,1};
        }
        if (location[0] == 720 && location[1] == 1266){
            return new int[] {2,2};
        }
        return new int[] {10000,10000};
    }

    protected void onScreenTouch() {
        findViewById(R.id.gameBoard).setOnTouchListener(
                new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent ev) {
                        if(ev.getAction() == MotionEvent.ACTION_DOWN){
                            Log.i("onTouchListener", "onTouch: " + " A touch has happened at: " + ev.getX() + ", " + ev.getY());
                            //onClick();

                        }
                        return true;
                    }
                }
        );

    }

    protected void moveObjectToCoordinates(int[] Destination) {
        ConstraintLayout gridLayout = findViewById(R.id.mainScreen);
        ImageView newSquare = new ImageView(this);
        newSquare.setAlpha(0f);
        newSquare.setScaleX(8 * 0.1f);
        newSquare.setScaleY(8 * 0.1f);
        newSquare.setX(360.0f);
        newSquare.setY(-360.0f);
        newSquare.setImageResource( IsFirstPlayer? R.drawable.red : R.drawable.yellow);
        gridLayout.addView(newSquare);
        float moveToX = Destination[0];
        float moveToY = Destination[1];
        newSquare.animate().alphaBy(1f).setDuration(1000).start();
        newSquare.animate().translationX(moveToX).setDuration(1000).start();
        newSquare.animate().translationY(moveToY).setDuration(1000).start();
    }
}





