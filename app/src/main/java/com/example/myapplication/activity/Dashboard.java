package com.example.myapplication.activity;

import com.example.myapplication.components.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.content.Categories;

public class Dashboard extends AppCompatActivity {
    private GridView categoryGridView;
    private Categories categories = new Categories();
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        actionBar.createActionBar("Dashboard", R.drawable.logo_icon, 0);
        categoryGridView = findViewById(R.id.categoryList);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories.getCategories());
        categoryGridView.setAdapter(categoryAdapter);
    }
}