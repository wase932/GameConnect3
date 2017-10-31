package com.example.toluwase.gameconnect3;

import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private float[] TouchCoord = new float[2];
    private ArrayList<float[]> AllViewCoords = new ArrayList<>();
    private boolean IsFirstPlayer = true;
    private List<Integer> AvailableSquares = new ArrayList<>();
    private List<Integer> PlayerOneSquares = new ArrayList<>();;
    private List<Integer> PlayerTwoSquares = new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        for (int i = 0; i < 9; i ++){
//            Log.i("Instantiating", "onCreate: " + (i + 1));
//            AvailableSquares.add(i + 1);
//        }
        Log.i("SIZE OF AvailabeSquares", "onCreate: " + AvailableSquares.size());
        getViewPositions();
        onScreenTouch(findViewById(R.id.gameBoard));
    }

    protected void getViewPositions() {
        float x;
        float y;
        String[] squareNames = new String [] {"One", "Two", "Three", "Four", "Five", "Six","Seven", "Eight", "Nine"};
        int availableSquare = 0;
        for (String squareName : squareNames) {
              final View view = getViewByResourceName(squareName);
              getViewPosition(view);
              AvailableSquares.add(availableSquare);
              availableSquare ++;
            Log.i("AvailableSquares", "Items: " + AvailableSquares.size() + "addingSquare No: " + availableSquare);

              outputArrayValues();
        }
    }

    protected void getViewPosition(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int[] locations = new int[2];
                        view.getLocationOnScreen(locations);
                        AllViewCoords.add(new float [] {locations[0], locations[1] - view.getWidth()/2});
                        Log.i("getViewPositions", "currentSquareCoord: " + locations[0] +";"+ locations[1]);
                    }       
                }
        );
    }
    private int getResourceIdByName(String name) {

        return getResources().getIdentifier(name, "id", this.getPackageName());
    }

    private View getViewByResourceName (String name) {
        return findViewById(getResourceIdByName(name));

    }

    protected void onScreenTouch(final View view ) {
        findViewById(R.id.gameBoard).setOnTouchListener(
                new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent ev) {
                        Log.i("onTouchListener", "onTouch: " + " A touch has happened at: " + ev.getX() + ", " + ev.getY());
                        View dart = findViewById(R.id.red);
                        TouchCoord[0] = ev.getX() - dart.getWidth()/2;
                        TouchCoord[1] = ev.getY() - dart.getHeight()/2;
                        float[] Destination = determineClosestCoordinates();
                        moveObjectToCoordinates(dart, Destination);
                        return true;
                    }
                }
        );

    }

    protected double measureDistance(float[] object1, float[] object2) {

        return Math.hypot(object1[0]-object2[0], object1[1]-object2[1]);
    }

    protected float [] determineClosestCoordinates() {
        float [] closestCoordinates = new float[]{};
        double smallestDistance = Double.MAX_VALUE;
        double currentDistance;
        int squarePosition = 0;
        for (float[] i : AllViewCoords)
        {
            currentDistance = measureDistance(TouchCoord,i);
            if (currentDistance < smallestDistance){
                smallestDistance = currentDistance;
                closestCoordinates = new float[] {i[0], i[1]};
                squarePosition = AllViewCoords.indexOf(i);

                Log.i("SelectedCoordinate", "NewCoordinates: " + closestCoordinates[0] + ", " + closestCoordinates[1]);
            }
        }
        // find the position of the coordinate in the list:
        if (IsFirstPlayer) {
            IsFirstPlayer = false;
            AvailableSquares.remove(squarePosition);
            PlayerOneSquares.add(squarePosition);
            Log.i("Play", "Player One Played Square: " + squarePosition );
            Log.i("Play", "Available Squares: " + AvailableSquares.size() );

        }
        else {
            IsFirstPlayer = true;
            AvailableSquares.remove(squarePosition);
            PlayerTwoSquares.add(squarePosition);
            Log.i("Play", "Player Two Played Square: " + squarePosition );
            Log.i("Play", "Available Squares: " + AvailableSquares.size() );
        }
        return closestCoordinates;
    }

        protected void outputArrayValues (){
            for (float [] i: AllViewCoords
                 ) {
                Log.i("ALLVIEWCOORDS", "index: " + "x: " + i[0] + " y: " + i[1]);

            }
        }

        protected void moveObjectToCoordinates(View object, float[] Destination) {
            View square = findViewById(R.id.One);
            float moveToX = Destination[0];
            float moveToY = Destination[1];
            object.animate().translationX(moveToX).setDuration(1000).start();
            object.animate().translationY(moveToY).setDuration(1000).start();
        }

        protected void play() {

        }
    }





