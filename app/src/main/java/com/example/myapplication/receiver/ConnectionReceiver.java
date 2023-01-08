package com.example.myapplication.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.utilities.CustomDialog;
import com.example.myapplication.utilities.NetworkUtil;

public class ConnectionReceiver extends BroadcastReceiver {
    static boolean isOnline = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        isOnline = NetworkUtil.getConnectivityStatusString(context);
        CustomDialog dialog = new CustomDialog(context, "There is no Internet connection\n" +
                "Please check your connection first");

        if (!isOnline) {
            dialog.showDialog();
        } else {
            dialog.dismissDialog();
        }
    }
}