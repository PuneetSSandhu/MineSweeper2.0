package com.example.minesweeper20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * CONTROLLER
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startgame = findViewById(R.id.startgame);
        startgame.setOnClickListener(this);
        Button options = findViewById(R.id.options);
        options.setOnClickListener(this);
        Button instructions = findViewById(R.id.instructions);
        instructions.setOnClickListener(this);
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startgame:
                Toast.makeText(this, "Starting New Game", Toast.LENGTH_SHORT).show();
                Intent intentgame = new Intent(MainActivity.this, MineSweeperGrid.class);
                startActivity(intentgame);
                break;
            case R.id.options:
                Toast.makeText(this, "Opening Options", Toast.LENGTH_SHORT).show();
                Intent intentoptions = new Intent(MainActivity.this, Options.class);
                startActivity(intentoptions);
                break;
            case R.id.instructions:
                Toast.makeText(this, "Open Instructions", Toast.LENGTH_SHORT).show();
                break;
            case R.id.title:
                Toast.makeText(this, "Nice Guy", Toast.LENGTH_SHORT).show();
                break;
    }
}


}
