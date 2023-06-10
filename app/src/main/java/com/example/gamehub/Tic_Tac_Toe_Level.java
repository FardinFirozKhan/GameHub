package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tic_Tac_Toe_Level extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_level);
        Button easyBtn= findViewById(R.id.easybtn);
        Button harrdBtn= findViewById(R.id.hardbtn);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tic_Tac_Toe_Level.this, Tic_Tac_Toe.class);
                startActivity(intent);
            }
        });

        harrdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tic_Tac_Toe_Level.this, Tic_Tac_Toe_hard.class);
                startActivity(intent);
            }
        });
    }
}