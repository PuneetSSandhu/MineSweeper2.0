package com.example.minesweeper20;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Options extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_screen);
        findViewById(R.id.submit).setOnClickListener(this);
//        findViewById(R.id.progress).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
//                // TODO Auto-generated method stub
//                TextView num = findViewById(R.id.progress);
//                num.setText(seekBar.getProgress());
//            }
//        });
//
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submit){
            String width = ((EditText)findViewById(R.id.width)).getText().toString();
            if(!width.equals(""))
                MineSweeperGrid.setCol(Integer.parseInt(width));
            String height = ((EditText)findViewById(R.id.height)).getText().toString();
            if(!height.equals(""))
                MineSweeperGrid.setRow(Integer.parseInt(height));
            String mines = ((EditText)findViewById(R.id.mines)).getText().toString();
            if(!mines.equals(""))
                MineSweeperGrid.setMines(Integer.parseInt(mines));
            Toast.makeText(this, "Saving Settings", Toast.LENGTH_SHORT).show();

        }
    }
}
