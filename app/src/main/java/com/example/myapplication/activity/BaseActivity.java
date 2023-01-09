package com.example.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.receiver.ConnectionReceiver;
import com.example.myapplication.service.BackgroundService;

public class BaseActivity extends AppCompatActivity {
    Intent intentService;

    private static final BroadcastReceiver connectionReceiver = new ConnectionReceiver();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(connectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        intentService = new Intent(BaseActivity.this, BackgroundService.class);
        startService(intentService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(connectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        intentService = new Intent(BaseActivity.this, BackgroundService.class);
        startService(intentService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.err.println("destroyed");
        unregisterReceiver(connectionReceiver);
        stopService(intentService);
    }
}