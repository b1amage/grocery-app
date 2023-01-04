package com.example.myapplication.activity;

import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.components.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.components.FilterCategory;
import com.example.myapplication.content.Categories;
import com.example.myapplication.content.Items;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    String[] categories = new String[]{"Vegetables", "Meat", "Fruit", "Diary", "Canned", "Snack", "Drinks", "Spice", "Household", "Personal hygiene"};
    private ListView categoryGridView;
    private Items items = new Items();
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
    private FilterCategory filterCategory = new FilterCategory(categories, this, R.layout.category_item);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        actionBar.createActionBar("Dashboard", R.drawable.logo_icon, 0);
        categoryGridView = findViewById(R.id.categoryList);
        CategoryItemAdapter categoryAdapter = new CategoryItemAdapter(this, items.getItems());
        categoryGridView.setAdapter(categoryAdapter);
        filterCategory.selectCategory();

    }
}