package com.example.minesweeper20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Vibrator;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Math.min;


public class MineSweeperGrid extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<SweeperButton> buttons = new ArrayList<>();
    static private int rows = 15;
    static private int cols = 10;
    static private int mines = 20;
    static private int flags = mines;
    private Vibrator vib;
    private MineSweeper mineSweeper;
    private GridLayout grid;
    private boolean flagMode = false;
    private boolean firstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        this.vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ImageButton flag = findViewById(R.id.flag);
        flag.setOnClickListener(this);
        int buttonDim = min(getScreenWidth()/cols, getScreenHeight()/rows);
        flag.requestLayout();
        flag.setScaleType(ImageView.ScaleType.FIT_XY);
        flag.getLayoutParams().height = buttonDim;
        flag.getLayoutParams().width = buttonDim;
        flag.setPadding(5,5,5,5);
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(this);
        this.mineSweeper = new MineSweeper(rows,cols,mines, this);
        this.grid = findViewById(R.id.gird);
        this.grid.setRowCount(rows);
        this.grid.setColumnCount(cols);

        for (int x=0; x<rows; x++) {
            for (int y=0; y<cols; y++) {
                SweeperButton b = new SweeperButton(this, x , y);
                b.setAdjustViewBounds(true);
                b.setOnClickListener(this);
                b.setPadding(5,5,5,5);
                b.setBackgroundColor(getResources().getColor(R.color.trans));
                this.grid.addView(b, buttonDim, buttonDim);
                b.setScaleType(ImageView.ScaleType.FIT_XY);
                b.setImageResource(R.drawable.tileempty);
                this.buttons.add(b);
            } // for
        } // for
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.reset){
            this.mineSweeper = new MineSweeper(rows,cols,mines,this);
            this.vib.vibrate(100);
            for(SweeperButton b:buttons){
                b.setEnabled(true);
                if(b.flagged){
                    b.setFlagged();
                }
            }
            redraw();
            MineSweeperGrid.flags = MineSweeperGrid.mines;
            flagMode = false;
            firstClick = true;
            Toast.makeText(this, ("Reset"), Toast.LENGTH_SHORT).show();
            return;
        }
        else if(v.getId() == R.id.flag){
            this.flagMode = !this.flagMode;
            this.vib.vibrate(10);
            Toast.makeText(this, ("Flag Mode"+flagMode), Toast.LENGTH_SHORT).show();
            return;
        }
        if (flagMode) {
                this.vib.vibrate(10);
                mineSweeper.setFlag(v);
                setImage((SweeperButton) v);
                if (mineSweeper.checkWinner()) {
                    endGame();
                    Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show();
                }
            return;
        }
        SweeperButton b = (SweeperButton)v;
        if(!((SweeperButton) v).flagged) {
            if (mineSweeper.getCell(getIdX(b), getIdY(b)) == 10) {
                if (firstClick) {
                    mineSweeper.reGen(getIdX(b), getIdY(b));
                    this.mineSweeper.openCell(getIdX(b), getIdY(b));
                    firstClick = false;
                    onClick(v);
                } else {
                    this.mineSweeper.openCell(getIdX(b), getIdY(b));
                    endGame();
                    redraw();
                    this.vib.vibrate(500);
                    Toast.makeText(this, "You lost", Toast.LENGTH_SHORT).show();

                }
                return;
            } else if (mineSweeper.getCell(getIdX(b), getIdY(b)) != 0) {
                this.mineSweeper.openCell(getIdX(b), getIdY(b));
                setImage(((SweeperButton) v));
            } else if (mineSweeper.getCell(getIdX(b), getIdY(b)) == 0) {
                this.mineSweeper.openCell(getIdX(b), getIdY(b));
                redraw();
            }
            v.setEnabled(false);
        }
        firstClick = false;
    }

    public static void setRow(int n){
        rows = n;
    }

    public static void setCol(int n){
        cols = n;
    }

    public static void setMines(int n){
        mines = n;
    }



    public ImageButton getButton(int[] id){
        for(SweeperButton b:buttons){
            if(b.getIndex()[0] == id[0] && b.getIndex()[1] == id[1]){
                return b;
            }
        }
        return null;
    }

    protected void redraw(){
        for(SweeperButton b:buttons){
            if(mineSweeper.getDrawCell(b.getIndex()[0],b.getIndex()[1]) != 9)
                setImage(b);
        }
    }

    private void setImage(SweeperButton b){
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b))== 10)
            b.setImageResource(R.drawable.tilemine);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 0)
            b.setImageResource(R.drawable.tileblank);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 1)
            b.setImageResource(R.drawable.tileone);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 2)
            b.setImageResource(R.drawable.tiletwo);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 3)
            b.setImageResource(R.drawable.tilethree);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 4)
            b.setImageResource(R.drawable.tilefour);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 5)
            b.setImageResource(R.drawable.tilefive);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 6)
            b.setImageResource(R.drawable.tilesix);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 7)
            b.setImageResource(R.drawable.tileseven);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 8)
            b.setImageResource(R.drawable.tileeight);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == 9)
            b.setImageResource(R.drawable.tileflag);
        if(mineSweeper.getDrawCell(getIdX(b), getIdY(b)) == -1)
            b.setImageResource(R.drawable.tileempty);
    }
    public int getIdX(SweeperButton b){return b.getIndex()[0];}

    public int getIdY(SweeperButton b){return b.getIndex()[1];}

    private void endGame(){
        for(SweeperButton b:buttons){
            mineSweeper.openCell(b.getIndex()[0],b.getIndex()[1]);
            b.setEnabled(false);
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static void putFlag(){
        MineSweeperGrid.flags--;
    }

    public static void pullFlag(){
        MineSweeperGrid.flags++;
    }

    public static int getFlag(){
        return MineSweeperGrid.flags;
    }

    public void toastFlag(){
        Toast.makeText(this, "You're out of flags", Toast.LENGTH_SHORT).show();
    }
}
