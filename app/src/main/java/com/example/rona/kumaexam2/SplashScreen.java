package com.example.rona.kumaexam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Intent bearsense = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(bearsense);
                finish();
            }
        };

        Handler bearhand = new Handler();
        bearhand.postDelayed(run,1000);

    }
}
