package com.example.myapplication.activity;

import androidx.annotation.NonNull;

import androidx.core.view.WindowCompat;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.ImageLoader;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ItemDetail extends BaseActivity {

    private Button btnShowSheet;
    private RelativeLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    private ImageView imageView;
    private TextView description;
    private TextView name;
    private TextView category;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ImageButton backButton;
    private TextView stock;


    private void initUIComponents() {
        btnShowSheet = findViewById(R.id.openBtn);
        bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        imageView = findViewById(R.id.detail_img);
        description = findViewById(R.id.detail_item_dsc);
        name = findViewById(R.id.item_detail_name);
        category = findViewById(R.id.category_tag);
        backButton = findViewById(R.id.detail_back_btn);
        stock = findViewById(R.id.item_detail_stock);
    }

    private void setUpBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetail.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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

    private void setUpContent(String img, String name, String category, String description, int quantity) {
        ImageLoader.loadImg(img, imageView);
        this.name.setText(name);
        this.category.setText(category);
        this.description.setText(description);
        this.stock.setText(String.format("Stock: %d", quantity));
    }

    private void getItemDetail() {
        (new APIHandler(ItemDetail.this)).getRequest("/item/detail/" + getIntent().getExtras().getString("_id"), new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                JSONObject jsonObject = response.getJSONObject("item");

                shimmerFrameLayout.stopShimmer();
                setContentView(R.layout.activity_item_detail);
                initUIComponents();
                handleDraggingSheet();
                handleButtonClick();
                setUpBackButton();
                setUpContent(jsonObject.getString("image"), jsonObject.getString("name"), jsonObject.getString("category"), jsonObject.getString("description"), jsonObject.getInt("quantity"));
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.detail_shimmer);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        shimmerFrameLayout = findViewById(R.id.detail_shimmer);

        shimmerFrameLayout.startShimmer();
        getItemDetail();


    }

}