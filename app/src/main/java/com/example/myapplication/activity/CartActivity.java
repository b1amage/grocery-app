package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartItemAdapter;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.content.Items;
import com.example.myapplication.db.DBManager;
import com.example.myapplication.model.Item;

import java.util.List;

public class CartActivity extends BaseActivity {
    private ListView listView;
    private ImageButton backBtn;
    private List<Item> items;
    private ImageButton minusBtn;
    private ImageButton addBtn;
    private ItemAdapter itemAdapter;
    private TextView quantity;

    private void initUIComponents() {
        listView = findViewById(R.id.cart_list_view);
        backBtn = findViewById(R.id.cart_back_btn);
        minusBtn = findViewById(R.id.cart_item_minus_btn);
        addBtn = findViewById(R.id.cart_item_plus_btn);
        quantity = findViewById(R.id.cart_item_quantity);
    }

    private void getCartItem() {
        System.out.println("Get cart called");
        items = (new DBManager(CartActivity.this)).fetchItemsFromCart();
        System.out.println(items);
    }


    private void setUpListView(List<Item> itemList) {
        System.out.println(itemList);
        itemAdapter = new ItemAdapter(itemList, true, CartActivity.this);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Calling set on item");
                Toast.makeText(CartActivity.this, "clicked", Toast.LENGTH_SHORT).show();
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
        getCartItem();
        initUIComponents();
        setUpListView(items);
        setBackButtonListener();

    }


}