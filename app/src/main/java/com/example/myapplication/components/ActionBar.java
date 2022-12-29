package com.example.myapplication.components;

import android.app.Activity;
import android.view.Gravity;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.utilities.Text;
import com.google.android.flexbox.FlexboxLayout;

public class ActionBar {
    private int actionBarId;
    private String title;
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

    public void createActionBar(String title){
        getActionBarElement();
        actionBarTitle.createTitle(24, title, R.color.black, Gravity.START);
    }
}
