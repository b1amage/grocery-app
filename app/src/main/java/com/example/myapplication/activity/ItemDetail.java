package com.example.myapplication.activity;

import androidx.annotation.NonNull;

import androidx.core.view.WindowCompat;


import android.os.Bundle;

import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class ItemDetail extends BaseActivity {

    private Button btnShowSheet;
    private RelativeLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    private void initUIComponents() {
        btnShowSheet = findViewById(R.id.openBtn);
        bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    private void handleDraggingSheet() {
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    btnShowSheet.setText("Collapse detail");
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    btnShowSheet.setText("View detail");
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void handleButtonClick() {
        btnShowSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_item_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initUIComponents();
        handleDraggingSheet();
        handleButtonClick();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

}