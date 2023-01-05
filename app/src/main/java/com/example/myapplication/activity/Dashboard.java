package com.example.myapplication.activity;

import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.components.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
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

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    String[] categories = new String[]{"Vegetables", "Meat", "Fruit", "Diary", "Canned", "Snack", "Drinks", "Spice", "Household", "Personal hygiene"};
    private ListView categoryView;
    private Items items = new Items();
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
        CategoryItemAdapter categoryAdapter = new CategoryItemAdapter(this, items.getItems());
        categoryView.setAdapter(categoryAdapter);
        filterCategory.selectCategory();

        cancelButton.createInactiveButton("Cancel", onClickCancelButton());
        deleteButton.createActiveButton("Yes, delete", onClickDeleteButton());
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