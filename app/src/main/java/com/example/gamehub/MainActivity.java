package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button snake_game= findViewById(R.id.snakeGame);
        Button wallBreaker=findViewById(R.id.wallBreaker);
        Button tic_tac_toe=findViewById(R.id.tic_tac_toe);
        snake_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SnakeGameLevel.class);
                startActivity(intent);
            }
        });

        wallBreaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, WallBreakerLevel.class);
                startActivity(intent);
            }
        });

        tic_tac_toe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Tic_Tac_Toe_Level.class);
                startActivity(intent);
            }
        });
    }
}