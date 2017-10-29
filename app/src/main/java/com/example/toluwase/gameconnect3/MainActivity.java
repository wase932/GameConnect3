package com.example.toluwase.gameconnect3;

import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private float touchX;
    private float touchY;
    private float[] touchCoord = new float[2];
    private float[] currentSquareCoord = new float[2];
    private ArrayList<float[]> allViewCoords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewPositions();
        onScreenTouch(findViewById(R.id.gameBoard));
//        for (float[] item: allViewCoords) {
//            Log.i("Position", "x: " + item[0] + " y: " + item[1]);
//        }
    }

    protected void getViewPositions() {
        float x;
        float y;
        // ArrayList<float[]> squareCoord = new ArrayList<>(9);
        String[] squareNames = new String [] {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        for (String squareName : squareNames) {
              final View view = getViewByResourceName(squareName);
              getViewPosition(view);
              outputArrayValues();
            //allViewCoords.add(getViewPosition(view));
//            Log.i("AddedNewCoord ", "new Added: " + getViewPosition(view)[0] + ", " + getViewPosition(view)[1] );
             // float [] squarePos = getViewPosition(view);
            
//             allViewCoords.add(squarePos);
//            Log.i("squarePos", "viewPosition: " + squarePos[0] + ", " + squarePos[1]);

//            Log.i("GetViewPosition", "Added Coordinates: " + getViewPosition(view)[0] + " ," + squarePos[1] );
            //Location.distanceBetween(touchX,touchY,squarePos[0], squarePos[1], distance);
        }
        // return squareCoord;
    }
    protected void getViewPosition(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int[] locations = new int[2];
                        view.getLocationOnScreen(locations);
                        currentSquareCoord[0] = locations[0];
                        currentSquareCoord[1] = locations[1];
                        allViewCoords.add(new float [] {currentSquareCoord[0], currentSquareCoord[1]});
                        Log.i("Adding Coord", "Adding New Coordinate to allViewCoords: ");
                         Log.i("getViewPositions", "currentSquareCoord: " + currentSquareCoord[0] +";"+ currentSquareCoord[1]);
                    }       
                }
        );
    }
    private int getResourceIdByName(String name) {

        int id = getResources().getIdentifier(name, "id", this.getPackageName());
        // Log.i("getResourceIdByName", "Name: " + name + " ID: " + id);
        return id;
    }

    private View getViewByResourceName (String name) {
        return findViewById(getResourceIdByName(name));
    }

    private String determineBestPlay () {
        return "Not Implemented";
    }

    protected void onScreenTouch(final View view ) {
        float [] touchPoints = new float[] {touchX,touchY};

        view.setOnTouchListener(
                new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent ev) {
                        Log.i("onTouchListener", "onTouch: " + " A touch has happened.");
                        touchX = ev.getX();
                        touchY = ev.getY();
                        Log.i("onTouchListener", "onTouch: " + touchX + ", " + touchY);
                        touchCoord[0] = ev.getX();
                        touchCoord[1] = ev.getY();
                        // outputArrayValues();
                        determineClosestCoordinates();
                        return true;
                    }
                }
        );

    }

    protected double measureDistance(float[] object1, float[] object2) {

        double distance = Math.hypot(object1[0]-object2[0], object1[1]-object2[1]);
        // Log.i("MeasureDistance", "Distance = " + distance);
        return distance;
    }

    protected void determineClosestCoordinates() {
//        Log.i("squareCoord", "lengthOfCoord: " + allViewCoords.size());
        float [] closestCoordinates= new float[2];
        double smallestDistance = 9999.9999;
        double currentDistance = 9999.9999;
//        Log.i("InitialDoubles", "smallestDist: " + smallestDistance);
//        Log.i("InitialDoubles", "currentDist: " + currentDistance);
//        Log.i("initialFloat", "closestCoord: " + closestCoordinates[0] + ", " +closestCoordinates[1]);

        for (float[] i : allViewCoords)
        {
             Log.i("CurrentSquare", "coordinates: " + i[0] + ", " + i[1]);
            currentDistance = measureDistance(touchCoord,i);
            if (currentDistance < smallestDistance){
                smallestDistance = currentDistance;
                Log.i("DetermineSmallest", "NewSmallestDist: " + smallestDistance);
                closestCoordinates = new float[] {i[0], i[1]};
                Log.i("SelectedCoordinate", "NewCoordinates: " + closestCoordinates[0] + ", " + closestCoordinates[1]);
            }
        }
    }

        protected void outputArrayValues (){
            for (float [] i: allViewCoords
                 ) {
                Log.i("ALLVIEWCOORDS", "index: " + "x: " + i[0] + " y: " + i[1]);

            }
        }
        // Log.i("determineClosest", "ClosestCoord: " + closestCoordinates[0] + ", " + closestCoordinates[1]);
    }





