package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.content.Items;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryDetails extends AppCompatActivity {
    private ActionBar detailsActionBar = new ActionBar(R.id.detailsActionBar, this);
    private ArrayList<Item> itemList = new Items().getItems();
    private LinearLayout addButton;
    private ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        itemListView = findViewById(R.id.categoryItemList);
        String title = getIntent().getStringExtra("actionBarTitle");
        detailsActionBar.createActionBar(title, R.drawable.ic_back, R.drawable.navbutton_shape);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryDetails.this, CreateItemForm.class);
                startActivity(intent);
            }
        });
        CategoryItemAdapter itemAdapter = new CategoryItemAdapter(this, itemList);
        itemListView.setAdapter(itemAdapter);
    }
}