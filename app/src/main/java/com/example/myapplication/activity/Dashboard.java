package com.example.myapplication.activity;

import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.components.ActionBar;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
//import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.components.FilterCategory;
import com.example.myapplication.content.Categories;
import com.example.myapplication.content.Items;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.ColorTransparentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends BaseActivity {
    private String[] categories = new Categories().getCategories();
    private ListView categoryView;
//    private Items items = new Items();
    private ImageButton addButton;
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
    private FilterCategory filterCategory = new FilterCategory(categories, this, R.layout.category_item);
    private Button cancelButton = new Button(R.id.cancelButton, this);
    private Button deleteButton = new Button(R.id.deleteButton, this);
    private RelativeLayout deleteNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        deleteNotification = findViewById(R.id.deleteNotification);
        deleteNotification.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(R.color.black,70)));

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(onClickAddButton());
        actionBar.createActionBar("Dashboard", R.drawable.logo_icon, 0);
        categoryView = findViewById(R.id.categoryList);
        CategoryItemAdapter categoryAdapter = new CategoryItemAdapter(this, Items.getItems());
        categoryView.setAdapter(categoryAdapter);
        filterCategory.selectCategory();

        cancelButton.createInactiveButton("Cancel", onClickCancelButton());
        deleteButton.createActiveButton("Yes, delete", onClickDeleteButton());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.items);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.items:
                        Toast.makeText(getApplicationContext(), "Items", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.vouchers:
                        Toast.makeText(getApplicationContext(), "Vouchers", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), VoucherManagement.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.orders:
                        Toast.makeText(getApplicationContext(), "Orders", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), OrderManagement.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.store:
                        Toast.makeText(getApplicationContext(), "Locations", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), LocationManagement.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private View.OnClickListener onClickAddButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PickCreateCategory.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener onClickCancelButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNotification.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
            }
        };
    }

    private View.OnClickListener onClickDeleteButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
            }
        };
    }
}