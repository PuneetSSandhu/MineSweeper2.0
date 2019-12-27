package com.example.minesweeper20;

import android.view.View;

import java.util.Random;
/**
 * MODEL
 */
public class MineSweeper {
    private Integer[][] board;
    private Integer[][] displayBoard;
    static private int rows;
    static private int cols;
    static private int mines;
    private MineSweeperGrid game;
    private int[][] minePos;

    public MineSweeper(int row, int col, int minenum, MineSweeperGrid game){
        rows = row;
        cols = col;
        this.game = game;
        this.board = new Integer[row][col];
        this.displayBoard = new Integer[row][col];
        mines=minenum;
        generateBoard();
    }

    /**
     * To generate the board you must place all mines first and then loop through to
     * place correct numbers in the tiles
     */
    private void generateBoard(){
        this.minePos = new int[mines][2];
        for(int i=0;i < rows; i++) {
            for (int j = 0; j < cols; j++){
                this.displayBoard[i][j] = -1;
                this.board[i][j] = 0;
            }
        }
        Random rand = new Random();
        for (int k=0;k < mines; k++){
            int x = rand.nextInt(rows);
            int y = rand.nextInt(cols);
            while (this.board[x][y] == 10) {
                x = rand.nextInt(rows);
                y = rand.nextInt(cols);
            }
            this.minePos[k] = (new int[]{x,y});
            this.board[x][y] = 10;
        }
        for(int i=0;i < rows; i++){
            for(int j=0;j < cols; j++){
                if(this.board[i][j] != null && this.board[i][j] != 10)
                    this.board[i][j]= touchingMines(i,j);
            }
        }
    }

    private int touchingMines(int row, int col){
        int count = 0;
        if(row != 0 && col != 0 && (10 == this.board[row-1][col-1]))
            count += 1;
        if(row != rows-1 && col != cols-1 && 10 == this.board[row+1][col+1])
            count += 1;
        if(row != rows-1 && col != 0 && 10 == this.board[row+1][col-1])
            count += 1;
        if(row != 0 && 10 == this.board[row-1][col])
            count += 1;
        if(row != rows-1 && 10 == this.board[row+1][col])
            count += 1;
        if(col != 0 && 10 == this.board[row][col-1])
            count += 1;
        if(col != cols-1 && 10 == this.board[row][col+1])
            count += 1;
        if(row != 0 && col != cols-1 && 10 == this.board[row-1][col+1])
            count += 1;
        return count;
    }

    public void sudoOpenCell(int x, int y){
        this.displayBoard[x][y] = this.board[x][y];
    }

    public void openCell(int i, int j){
        sudoOpenCell(i,j);
        game.getButton(new int[]{i,j}).setEnabled(false);
        if(this.board[i][j] == 0) {
            if (i != 0 && j != 0 && this.displayBoard[i - 1][j - 1] != this.board[i - 1][j - 1])
                openCell(i - 1, j - 1);
            if (i != rows - 1 && j != cols - 1 && this.displayBoard[i + 1][j + 1] != this.board[i + 1][j + 1])
                openCell(i + 1, j + 1);
            if (i != rows - 1 && j != 0 && this.displayBoard[i + 1][j - 1] != this.board[i + 1][j - 1])
                openCell(i + 1, j - 1);
            if (i != 0 && this.displayBoard[i - 1][j] != this.board[i - 1][j])
                openCell(i - 1, j);
            if (j != 0 && this.displayBoard[i][j - 1] != this.board[i][j - 1])
                openCell(i, j - 1);
            if (j != cols - 1 && this.displayBoard[i][j + 1] != this.board[i][j + 1])
                openCell(i, j + 1);
            if (i != rows - 1 && this.displayBoard[i + 1][j] != this.board[i + 1][j])
                openCell(i + 1, j);
            if (i != 0 && j != cols - 1 && this.displayBoard[i - 1][j + 1] != this.board[i - 1][j + 1])
                openCell(i - 1, j + 1);
        }
    }
    protected void setFlag(View v){
        SweeperButton b = (SweeperButton) v;
        if(MineSweeperGrid.getFlag() > 0 || b.flagged) {
            if (!b.flagged) {
                MineSweeperGrid.putFlag();
                this.displayBoard[b.getIndex()[0]][b.getIndex()[1]] = 9;
            } else {
                this.displayBoard[b.getIndex()[0]][b.getIndex()[1]] = -1;
                MineSweeperGrid.pullFlag();
            }
            b.setFlagged();
        }
    }

    public boolean checkWinner(){
        int count =0;
        for(int[] pos:this.minePos){
            if(this.displayBoard[pos[0]][pos[1]] == 9){
                count++;
            }
        }
        return (count == mines);
    }

    public static int getRow(){
        return rows;
    }
    
    public static int getCol(){
        return cols;
    }

    protected int getCell(int row, int col){
        return this.board[row][col];
    }

    protected void reGen(int row, int col){
        while(board[row][col] == 10)
            generateBoard();
    }

    protected void reset(){

    }

    protected int getDrawCell(int row, int col){
        return this.displayBoard[row][col];
    }

}
