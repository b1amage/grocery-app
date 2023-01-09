package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private List<Item> itemList;
    private TextView seeAllText;

    private void initUIComponents() {
        listView = findViewById(R.id.item_listview);
        seeAllText = findViewById(R.id.see_all_txt);
    }

    private void initMockData() {
        itemList = new ArrayList<>(
                Arrays.asList(
                        new Item("6d3", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12),
                        new Item("6d4", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12),
                        new Item("6d5", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12),
                        new Item("6d6", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12)
                )
        );
    }

    private void setUpListView() {
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setViewAllTextListener() {
        seeAllText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewAllItemActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();
        initMockData();
        setUpListView();
        setViewAllTextListener();
    }
}