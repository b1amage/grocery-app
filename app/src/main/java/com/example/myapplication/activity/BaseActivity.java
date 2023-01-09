package com.example.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.receiver.ConnectionReceiver;

public class BaseActivity extends AppCompatActivity {
    private static final BroadcastReceiver connectionReceiver = new ConnectionReceiver();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(connectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.err.println("destroyed");
        unregisterReceiver(connectionReceiver);
    }
}