package com.example.minesweeper20;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageButton;

@SuppressLint("AppCompatCustomView")
public class SweeperButton extends ImageButton{
    private int row;
    private int col;
    public boolean flagged = false;
    public SweeperButton(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;
    }

    public int[] getIndex(){
        return new int[]{this.row,this.col};
    }

    public void setFlagged(){
        this.flagged = !this.flagged;
    }
}
