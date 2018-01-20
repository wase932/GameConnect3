package com.xlivix.toluwase.gameconnect3;

import android.content.*;
import android.os.*;
import android.support.constraint.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private boolean IsFirstPlayer = true;
    private boolean IsSquareClickable = true;
    Game game = new Game(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getClickedView(View view){
        if(IsSquareClickable) {
            Log.i("ClickedView", "view : " + view.getId());
            int[] location = new int[2];
            view.getLocationInWindow(location);
            //Prevent view from being clicked again(disable play on same square)
            view.setClickable(false);
            int[] boardCoord = convertLocationToBoardCoord(location);
            int animationDestinationX = location[0] - (view.getWidth() / 16); // 16: Magic number to position piece
            int animationDestinationY = location[1] - (view.getHeight() / 4);
            moveObjectToCoordinates(new int[]{animationDestinationX, animationDestinationY});
            Log.i("ClickedView", "Location Is : " + location[0] + ", " + location[1]);
            Log.i("ClickedView", "boardLocation Is : " + boardCoord[0] + ", " + boardCoord[1]);
            final Game.State result = game.Move(boardCoord[0], boardCoord[1], IsFirstPlayer ? Game.State.X : Game.State.O);
            IsFirstPlayer = !IsFirstPlayer;
            Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (result == Game.State.Blank) {
                            Toast.makeText(getApplicationContext(), "Draw!", Toast.LENGTH_LONG).show();
                            IsSquareClickable = false;
                            displayAlert("Game Over", "Draw!!! Would you like to play Again?", Game.State.Blank);
                        }
                        if (result == Game.State.X) {
                            Toast.makeText(getApplicationContext(), "Red Wins!", Toast.LENGTH_LONG).show();
                            IsSquareClickable = false;
                            displayAlert("Game Over", "Red Won!!! Would you like to play Again?", Game.State.X);
                        }
                        if (result == Game.State.O) {
                            Toast.makeText(getApplicationContext(), "Yellow Wins!", Toast.LENGTH_LONG).show();
                            IsSquareClickable = false;
                            displayAlert("Game Over", "Yellow Won!!! Would you like to play Again?", Game.State.O);
                        }
                    }
                },500);
        }
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

    void displayAlert(String title, String message, Game.State winner) {
        int icon = winner == Game.State.X? R.drawable.red : R.drawable.yellow;
        if(winner == Game.State.Blank)
            icon = 0;
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                    }
                })
                .setIcon( icon)
                .show();
    }
}


