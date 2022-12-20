package com.example.myapplication.utilities;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

public class Text {
    private int textId;
    private Activity activity;
    private TextView text;

    public Text(int textId, Activity activity) {
        this.textId = textId;
        this.activity = activity;
    }

    private void getTextElement(){
        this.text = this.activity.findViewById(textId);
    }

    public void createTitle(int size, String title, int textColor){
        getTextElement();

        text.setText(title);
        text.setTextColor(text.getResources().getColor(textColor));
        text.setTextSize(size);
    }

    public void createText(int size, String title, int textColor){
        getTextElement();
        text.setTypeface(text.getTypeface(), Typeface.NORMAL);
        text.setText(title);
        text.setTextColor(text.getResources().getColor(textColor));
        text.setTextSize(size);
    }
}
