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

    public String getRole() {
        return sharedPreferences.getString("role", "");
    }

    public String getUserId() {
        return sharedPreferences.getString("userId", "");
    }

    public String getName() {
        return sharedPreferences.getString("name", "");
    }

    public Long getLoginAt() {
        return sharedPreferences.getLong("loginAt", 0);
    }

    public void expireCookie() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("refreshToken");
        editor.remove("role");
        editor.remove("userId");
        editor.remove("name");
        editor.remove("loginAt");
        editor.apply();
    }
}
