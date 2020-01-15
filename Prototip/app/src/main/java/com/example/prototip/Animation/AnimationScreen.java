package com.example.prototip.Animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.prototip.HomeAcivity;
import com.example.prototip.MainActivity;
import com.example.prototip.R;

public class AnimationScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=2500;
    AnimationDrawable wifiAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_screen);




        ImageView imageView =(ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        wifiAnimation = (AnimationDrawable)imageView.getBackground();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AnimationScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        wifiAnimation.start();
    }
}
