package com.example.myapplication.components;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.activity.Dashboard;
import com.example.myapplication.utilities.Text;
import com.google.android.flexbox.FlexboxLayout;

public class ActionBar {
    private int actionBarId;
    private Activity activity;

    private FlexboxLayout actionBarLayout;
    private ImageView actionBarButton;
    private Text actionBarTitle;

    public ActionBar(int actionBarId, Activity activity) {
        this.actionBarId = actionBarId;
        this.activity = activity;
    }

    private void getActionBarElement(){
        actionBarLayout = activity.findViewById(actionBarId);
        actionBarTitle = new Text(R.id.actionBarTitle, activity);
        actionBarButton = actionBarLayout.findViewById(R.id.actionBarButton);
    }

    public void createActionBar(String title, int iconButton, int background){
        getActionBarElement();
        actionBarTitle.createTitle(24, title, R.color.black, Gravity.START);
        actionBarButton.setImageResource(iconButton);

        if (background != 0){
            actionBarButton.setBackgroundResource(background);
            actionBarButton.setPadding(20, 20, 20, 20);
        }

        if (iconButton != R.drawable.logo_icon){
            actionBarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity.getApplicationContext(), Dashboard.class);
                    activity.startActivity(intent);
                }
            });
        }
    }
}
