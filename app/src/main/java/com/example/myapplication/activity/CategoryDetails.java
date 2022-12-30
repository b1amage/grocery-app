package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryDetails extends AppCompatActivity {
    private ActionBar detailsActionBar = new ActionBar(R.id.detailsActionBar, this);
    ArrayList<Item> itemList;
    private ListView itemListView;
    private void initMockData() {
        itemList = new ArrayList<>(
                Arrays.asList(
                        new Item(1, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(2, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(3, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(4, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(5, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(1, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(2, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(3, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(4, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(5, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12)
                )
        );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        initMockData();
        itemListView = findViewById(R.id.categoryItemList);
        String title = getIntent().getStringExtra("actionBarTitle");
        detailsActionBar.createActionBar(title, R.drawable.ic_back, R.drawable.navbutton_shape);

        CategoryItemAdapter itemAdapter = new CategoryItemAdapter(this, itemList);
        itemListView.setAdapter(itemAdapter);
    }
}