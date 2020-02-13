package com.avn;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static int[][] sudoku = new int[9][9]; // Y & X

    public static void main(String[] args) {

        sudoku[0][5] = 2;
        sudoku[0][6] = 1;

        sudoku[1][2] = 4;
        sudoku[1][5] = 8;
        sudoku[1][6] = 7;

        sudoku[2][1] = 2;
        sudoku[2][3] = 3;
        sudoku[2][6] = 9;

        sudoku[3][0] = 6;
        sudoku[3][2] = 2;
        sudoku[3][5] = 3;
        sudoku[3][7] = 4;

        sudoku[5][1] = 5;
        sudoku[5][3] = 6;
        sudoku[5][6] = 3;
        sudoku[5][8] = 1;

        sudoku[6][2] = 3;
        sudoku[6][5] = 5;
        sudoku[6][7] = 8;

        sudoku[7][2] = 8;
        sudoku[7][3] = 2;
        sudoku[7][6] = 5;

        sudoku[8][2] = 9;
        sudoku[8][3] = 7;

        System.out.println("Original board:");
        for(int[] row : sudoku){
            for(int cell : row){
                System.out.print(cell);
            }
            System.out.println();
        }

        solve();

        System.out.println("Solved board:");
        for(int[] row : sudoku){
            for(int cell : row){
                System.out.print(cell);
            }
            System.out.println();
        }



    }

    static boolean isInRow(int row, int number){
        for(int i = 0; i != 9; i++){
            if(sudoku[row][i] == number){
                return true;
            }
        }
        return false;
    }

    static boolean isInColumn(int column, int number){
        for(int i = 0; i != 9; i++){
            if(sudoku[i][column] == number){
                return true;
            }
        }
        return false;
    }

    static boolean isInBox(int row, int column , int number){

        int r = row - row % 3; // Gets what section it's in (0, 3, or 6)
        int c = column - column % 3;

        for(int i = r; i != r+3; i++){
            for(int j = c; j != c+3; j++){

                if(sudoku[i][j] == number){
                    return true;
                }

            }
        }

        return false;
    }

    static boolean solve(){

        for(int row = 0; row != 9; row++){
            for(int column = 0; column != 9; column++){
                if(sudoku[row][column] == 0){ //Find the first cell without a number.
                    for(int number = 1; number != 10; number ++){ //Cycle through the numbers 1-9.
                        if( !(isInBox(row, column, number) || isInRow(row, number) || isInColumn(column, number) ) ){ //Check if the number is a valid number,
                            sudoku[row][column] = number; //If it's valid, set it.
                            if(solve()){ //Asks to solve the rest of the board.
                                return true; //If it does so successfully, then we're all good and we pass the "true" up through the recursions up to the initial call.
                            } else {
                                sudoku[row][column] = 0;

                                //If a cell in the recursive call cannot be filled with any 1-9 number, the cell that the recursive call was made at is reset to 0.
                                //This means the algorithm can continue to iterate through the numbers 1-9.
                                //(If the algorithm found 5 to be valid number, but recursive calls proved it to be incorrect, it would be "backtracked" to 5,
                                //then 6, 7, 8, and 9 could be checked.)
                                //Since this is recursive, then if the hypothetical, aforementioned 5 were to reach 9 and still not be a valid number to add to the board,
                                //it would return false to the PREVIOUS recursion yet again, and THAT recursion would be allowed to iterate.
                                //This allows every branch to be explored. Even if the algorithm reached the penultimate cell and realized it was invalid,
                                //Recursion would allow it to jump all the way back to the very 1st cell, increment it by 1, and explore the next branch.
                                //(It wouldn't do that instantly, mind. It would increment and test every previous cell one-by-one before eventually reaching the first cell).
                                //Magical!!!

                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;

    }
}
