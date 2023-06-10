package com.example.gamehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WallBreakerLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_breaker_level);
        Button easyBtn= findViewById(R.id.easybtn);
        Button harrdBtn= findViewById(R.id.hardbtn);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WallBreakerLevel.this, WallBreaker.class);
                startActivity(intent);
            }
        });

        harrdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WallBreakerLevel.this, WallBreakerHard.class);
                startActivity(intent);
            }
        });
    }
}