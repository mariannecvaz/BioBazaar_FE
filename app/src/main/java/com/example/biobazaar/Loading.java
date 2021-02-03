package com.example.biobazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Loading extends AppCompatActivity {
    private static int SPLASH_TIME = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView img;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getSupportActionBar().hide();

        img = (ImageView)findViewById(R.id.logoLeft);
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in1);
        img.startAnimation(fadeIn);

        img = (ImageView)findViewById(R.id.logoMiddle);

        ImageView finalImg = img;
        Animation fadeIn1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in2);
        finalImg.startAnimation(fadeIn1);

        img = (ImageView)findViewById(R.id.logoRight);
        ImageView finalImg1 = img;
        Animation fadeIn2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in3);
        finalImg1.startAnimation(fadeIn2);

        img = (ImageView)findViewById(R.id.logoBioBazaar);
        ImageView finalImg2 = img;
        Animation fadeIn3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in4);
        finalImg2.startAnimation(fadeIn3);


        img = (ImageView)findViewById(R.id.logoPhrase);
        ImageView finalImg3 = img;
        Animation fadeIn4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in5);
        finalImg3.startAnimation(fadeIn4);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Loading.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, SPLASH_TIME);
    }
}