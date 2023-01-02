package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewAllItemActivity extends AppCompatActivity {

    private List<Item> itemList;
    private ListView listView;
    private ImageButton backBtn;


    private void initUIComponents() {
        listView = findViewById(R.id.view_all_list_view);
        backBtn = findViewById(R.id.view_all_back_btn);
    }

    private void initMockData() {
        itemList = new ArrayList<>(
                Arrays.asList(
                        new Item(1, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(2, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(3, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(4, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(5, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(6, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(7, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(8, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(9, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12),
                        new Item(10, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12)
                )
        );
    }

    private void setUpListView() {
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewAllItemActivity.this, "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBackButtonListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllItemActivity.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_item);

        initUIComponents();
        initMockData();
        setUpListView();
        setBackButtonListener();
    }
}