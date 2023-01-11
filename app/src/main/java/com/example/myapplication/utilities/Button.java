package com.example.myapplication.utilities;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class Button {
    protected int buttonId;
    protected Activity activity;
    protected LinearLayout button;

    private ImageView buttonIcon;
    private TextView buttonText;

    public Button(int buttonId, Activity _activity) {
        this.buttonId = buttonId;
        this.activity = _activity;
    }

    private void getButtonElement(){
        button = activity.findViewById(buttonId);
        buttonIcon = button.findViewById(R.id.button_icon);
        buttonText = button.findViewById(R.id.button_text);
    }

    private ColorStateList setColor(int color){
        return ContextCompat.getColorStateList(this.activity.getApplicationContext(), color);
    }
    // custom active button
    public void createActiveButton(String text, View.OnClickListener onClickFunction){
        getButtonElement();

        button.setBackgroundTintList(setColor(R.color.primary_100));
        buttonIcon.setVisibility(View.GONE);
        buttonText.setTextColor(Color.WHITE);
        buttonText.setText(text);
        buttonText.setTypeface(buttonText.getTypeface(), Typeface.BOLD);

        button.setOnClickListener(onClickFunction);
    }

    // custom inactive button
    public void createInactiveButton(String text, View.OnClickListener onClickFunction){
        getButtonElement();
        button.setBackgroundTintList(setColor(R.color.white));
        buttonIcon.setVisibility(View.GONE);
        buttonText.setText(text);
        buttonText.setTypeface(buttonText.getTypeface(), Typeface.BOLD);

        button.setOnClickListener(onClickFunction);

    }

    // custom navigation button
    public void createNavbarButton(int icon, String text, View.OnClickListener onClickFunction){
        getButtonElement();

        buttonIcon.setImageResource(icon);
        button.setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.navbutton_shape));
        buttonText.setText(text);
        buttonText.setTypeface(buttonText.getTypeface(), Typeface.BOLD);

        button.setOnClickListener(onClickFunction);
    }

    // create button
    public void createButton(int icon, View.OnClickListener onClickFunction){
        getButtonElement();

        button.setBackground(ContextCompat.getDrawable(activity.getApplicationContext(),R.drawable.navbutton_shape));
        buttonIcon.setImageResource(icon);
        buttonText.setVisibility(View.GONE);

        button.setOnClickListener(onClickFunction);
    }
}
