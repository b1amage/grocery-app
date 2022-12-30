package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    private static final long DURATION = 2000;
    ImageView logo;

    private void initUIComponents() {
        logo = findViewById(R.id.splash_logo);
    }

    private void runSplashAnimation() {
        logo.animate().alpha(0).setDuration(DURATION + 100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, OTPActivity.class));
                finish();
            }
        }, DURATION);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUIComponents();
        runSplashAnimation();
    }
}