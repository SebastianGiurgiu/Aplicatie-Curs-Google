package com.example.prototip;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withHeaderText("Welcome to my Android app")
                .withBackgroundColor(Color.parseColor("#1a1b29"))
                .withLogo(R.mipmap.ic_launcher_round);



        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getHeaderTextView().setTextSize(30);

        View easy = config.create();
        setContentView(easy);

    }
}
