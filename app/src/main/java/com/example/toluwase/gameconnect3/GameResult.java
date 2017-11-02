package com.example.toluwase.gameconnect3;

import android.util.*;

/**
 * Created by Toluwase on 11/1/17.
 */

public class GameResult {
    enum State{Blank, X, O};

    static int n = 3;
    static State[][] board = new State[n][n];
    static int moveCount;

    static void Move(int x, int y, State s){
        Log.i("Game", "MoveMade: " + x + ", " + y);
        if(board[x][y] == State.Blank){
            board[x][y] = s;
        }
        moveCount++;
        Log.i("Game", "MoveCount: " + moveCount);

        //check end conditions

        //check col
        for(int i = 0; i < n; i++){
            if(board[x][i] != s)
                break;
            if(i == n-1){
                //report win for s
                Log.i("GameResult", "Win: " + " Player Wins the Game on Column");
            }
        }

        //check row
        for(int i = 0; i < n; i++){
            if(board[i][y] != s)
                break;
            if(i == n-1){
                //report win for s
                Log.i("GameResult", "Win: " + " Player Wins the Game on row");
            }
        }

        //check diag
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < n; i++){
                if(board[i][i] != s)
                    break;
                if(i == n-1){
                    //report win for s
                    Log.i("GameResult", "Win: " + " Player Wins the Game on Diagonal");

                }
            }
        }

        //check anti diag
        if(x + y == n - 1){
            for(int i = 0;i<n;i++){
                if(board[i][(n-1)-i] != s)
                    break;
                if(i == n-1){
                    //report win for s
                    Log.i("GameResult", "Win: " + " Player Wins the Game on Anti-Diagonal");

                }
            }
        }

        //check draw
        if(moveCount == (n^2 - 1)){
            //report draw
            Log.i("GameResult", "Draw: " + " Game is a Draw!");
        }
    }
}
