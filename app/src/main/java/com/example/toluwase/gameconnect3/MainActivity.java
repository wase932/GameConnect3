package com.example.toluwase.gameconnect3;

import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private float[] touchCoord = new float[2];
    private ArrayList<float[]> allViewCoords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewPositions();
        onScreenTouch(findViewById(R.id.gameBoard));
    }

    protected void getViewPositions() {
        float x;
        float y;
        String[] squareNames = new String [] {"One", "Two", "Three", "Four", "Five", "Six","Seven", "Eight", "Nine"};
        for (String squareName : squareNames) {
              final View view = getViewByResourceName(squareName);
              getViewPosition(view);
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
                        allViewCoords.add(new float [] {locations[0], locations[1]});
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
                        Log.i("onTouchListener", "onTouch: " + " A touch has happened at: " + ev.getX() + ", " + ev.getY());;
                        touchCoord[0] = ev.getX();
                        touchCoord[1] = ev.getY();
                        float[] fakeDest = new float[] {0.0f, 210.0f};
                        float[] Destination = determineClosestCoordinates();
                        moveObjectToCoordinates(findViewById(R.id.red), Destination);
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
        for (float[] i : allViewCoords)
        {
            currentDistance = measureDistance(touchCoord,i);
            if (currentDistance < smallestDistance){
                smallestDistance = currentDistance;
                closestCoordinates = new float[] {i[0], i[1]};
                Log.i("SelectedCoordinate", "NewCoordinates: " + closestCoordinates[0] + ", " + closestCoordinates[1]);
            }
        }
        return closestCoordinates;
    }

        protected void outputArrayValues (){
            for (float [] i: allViewCoords
                 ) {
                Log.i("ALLVIEWCOORDS", "index: " + "x: " + i[0] + " y: " + i[1]);

            }
        }

        protected void moveObjectToCoordinates(View object, float[] Destination) {
            View square = findViewById(R.id.One);
            float moveToX = Destination[0];// - (square.getWidth()/2);
            float moveToY = Destination[1] - (square.getWidth()/2);
            object.animate().translationX(moveToX).setDuration(1000).start();
            object.animate().translationY(moveToY).setDuration(1000).start();
            //TODO: Bug in Fetching Coordinates. Need to send the coordinates at 1/2
        }
    }





