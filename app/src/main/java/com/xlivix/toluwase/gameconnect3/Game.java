package com.xlivix.toluwase.gameconnect3;

import android.util.*;

/**
 * Created by Toluwase on 11/1/17.
 */

class Game {
    private int n;
    private State[][] board;
    private int moveCount = 0;
    enum State{Blank, X, O}

    Game(int boardSize) {
        n = boardSize;
        Log.i("NValueOn Initialize", "n : " + n);
        board = new State[n][n];

        Log.i("GamePlay ", "Initializing Game Board : Size is " + n);
        board[0][0] = State.Blank;
        board[0][1] = State.Blank;
        board[0][2] = State.Blank;
        board[1][0] = State.Blank;
        board[1][1] = State.Blank;
        board[1][2] = State.Blank;
        board[2][0] = State.Blank;
        board[2][1] = State.Blank;
        board[2][2] = State.Blank;
        Log.i("GamePlay ", "Game Board has been Initialized");
    }

    State Move(int x, int y, State s){
        System.out.println("Play Made! Player: " + s + " x: " + x + " y: " + y);
        if(board[x][y] == State.Blank){
            board[x][y] = s;
        }
        moveCount++;

    //check end conditions
        //check col
        for(int i = 0; i < n; i++){
            if(board[x][i] != s)
                break;
            if(i == n-1){
                //report win for s
                System.out.println("Winner Col: " + s);
                return s;
            }
        }

        //check row
        for(int i = 0; i < n; i++){
            if(board[i][y] != s)
                break;
            if(i == n-1){
                //report win for s
                System.out.println("Winner row: " + s);
                return s;
            }
        }

        //check diagonal
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < n; i++){
                if(board[i][i] != s)
                    break;
                if(i == n-1){
                    //report win for s
                    System.out.println("Winner diag: " + s);
                    return s;
                }
            }
        }

        //check anti diagonal
        if(x + y == n - 1){
            for(int i = 0;i<n;i++){
                if(board[i][(n-1)-i] != s)
                    break;
                if(i == n-1){
                    //report win for s
                    System.out.println("Winner anti diag: " + s);
                    return s;
                }
            }
        }
        Log.i("MOVE", "MoveCount: " + moveCount);
        Log.i("ValueOfN", "NVALUE: " + n + " | N^2: " + (n^2) + " | Using Power: " + Math.pow(n, 2));

        //check draw
        if(moveCount == Math.pow(n, 2)){
            //report draw
            System.out.println("DRAW : " + s + " MoveCount: " + moveCount + "\n n => " + n);
            return State.Blank;
        }
        return null;
    }
}
