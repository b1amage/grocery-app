package com.example.myapplication.utilities;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.myapplication.R;

public class CustomDialog {
    private Context context;
    private String message;
    private static Dialog dialog = null;

    public CustomDialog(Context context, String message) {
        if (dialog != null) {
            dialog.dismiss();
        }
        this.context = context;
        this.message = message;
    }

    public void showDialog(){
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.internet_failed_dialog);

        TextView text = dialog.findViewById(R.id.error_text_dialog);
        text.setText(message);

        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            Log.i("dialog:", "close");
            dialog.dismiss();
        }
    }
}