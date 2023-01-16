package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.utilities.CookieManager;

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
                boolean isLogin = (new CookieManager(SplashActivity.this)).isLogin();
                if (!isLogin) {
                    startActivity(new Intent(SplashActivity.this, Dashboard.class));
                } else {
//                    startActivity(new Intent(SplashActivity.this, SendFeedbackActivity.class));
                    String role = (new CookieManager(SplashActivity.this)).getRole();
                    if (role.equals("customer")) {
                        startActivity(new Intent(SplashActivity.this, Dashboard.class));
                    } else if (role.equals("staff")) {
                        startActivity(new Intent(SplashActivity.this, Dashboard.class));
                    }
                }
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