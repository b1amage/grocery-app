package com.example.myapplication.service;

import android.app.Service;
import android.content.*;
import android.os.*;
import android.util.Log;

import com.example.myapplication.utilities.CookieManager;

import java.util.Date;

public class BackgroundService extends Service {

    public Context context;
    public Handler handler = null;
    public static Runnable runnable = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("created", "created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("start", "start");
        context = getApplicationContext();
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Log.i("run", "running");
                Log.i("token", (new CookieManager(context)).isLogin() ? "login" : "not login");
                long loginAt = (new CookieManager(context)).getLoginAt();
                double daysToExpire = 30;
                long expiresAt = (long) (loginAt + daysToExpire * 24 * 3600 * 1000);
                long current = (new Date()).getTime();

                if (current >= expiresAt) {
                    Log.i("expireAt", String.valueOf(expiresAt));
                    Log.i("current", String.valueOf(current));
                    (new CookieManager(context)).expireCookie();
                }
                handler.postDelayed(runnable, 1000);
            }
        };

        handler.postDelayed(runnable, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        Log.i("destroy", "destroy");
        handler.removeCallbacks(runnable);
    }
}