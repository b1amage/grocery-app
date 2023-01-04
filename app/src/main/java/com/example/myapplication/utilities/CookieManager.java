package com.example.myapplication.utilities;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class CookieManager {
    private Context ctx;
    private SharedPreferences sharedPreferences;

    public CookieManager(Context ctx) {
        this.ctx = ctx;
        this.sharedPreferences = ctx.getSharedPreferences("Cookies", MODE_PRIVATE);
    }

    public boolean isLogin() {
        String refreshToken = sharedPreferences.getString("refreshToken", "");
        return !refreshToken.equals("") && !refreshToken.equals("logout");
    }
}
