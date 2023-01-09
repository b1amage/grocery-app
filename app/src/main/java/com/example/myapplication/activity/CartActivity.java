package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.content.Items;
import com.example.myapplication.model.Item;

import java.util.List;

public class CartActivity extends BaseActivity {
    private ListView listView;
    private List<Item> itemList;
    private ImageButton backBtn;

    private void initUIComponents() {
        listView = findViewById(R.id.cart_list_view);
        backBtn = findViewById(R.id.cart_back_btn);
    }

    private void initMockData() {
        itemList = Items.getItems();
    }

    private void setUpListView() {
        ItemAdapter itemAdapter = new ItemAdapter(itemList, true);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CartActivity.this, "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBackButtonListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initUIComponents();
        initMockData();
        setUpListView();
        setBackButtonListener();
    }


}