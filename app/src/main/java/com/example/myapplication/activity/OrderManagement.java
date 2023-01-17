package com.example.myapplication.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.adapter.VoucherAdapter;
import com.example.myapplication.components.ActionBar;
//import com.example.myapplication.components.FilterCategory;
import com.example.myapplication.content.Categories;
import com.example.myapplication.content.Orders;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.ColorTransparentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderManagement extends BaseActivity {
    private ArrayList<Order> orders = new Orders().getOrders();
    private ListView ordersView;
    private ImageButton addButton;
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
//    private FilterCategory filterCategory = new FilterCategory(categories, this, R.layout.category_item);
    private Button cancelButton = new Button(R.id.cancelButton, this);
    private Button deleteButton = new Button(R.id.deleteButton, this);
    private RelativeLayout deleteNotification;

    private EditText searchBox;
    private ImageButton searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);
        setUpSearchBtn();

        deleteNotification = findViewById(R.id.deleteNotification);
        deleteNotification.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(R.color.black,70)));

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(onClickAddButton());
        actionBar.createActionBar("Dashboard", R.drawable.logo_icon, 0);

        ImageButton logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        ordersView = findViewById(R.id.categoryList);
        OrderAdapter categoryAdapter = new OrderAdapter(this, orders);
        ordersView.setAdapter(categoryAdapter);
        ordersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Order order = (Order) ordersView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                intent.putExtra("order", order);
                System.out.println(order);
                startActivity(intent);
            }
        });

        ImageButton viewFeedbackButton = findViewById(R.id.viewFeedback);
        viewFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewFeedbackActivity.class));
            }
        });

        cancelButton.createInactiveButton("Cancel", onClickCancelButton());
        deleteButton.createActiveButton("Yes, delete", onClickDeleteButton());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.orders);

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

    private void setUpSearchBtn() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();

                ArrayList<Order> itemArrayList = new ArrayList<>();

                if (query.isEmpty()){
                    itemArrayList.addAll(orders);
                } else{
                    for (Order o : orders){
                        if (o.get_id().toLowerCase().contains(query.toLowerCase())){
                            itemArrayList.add(o);
                        }
                    }
                }

                OrderAdapter itemAdapter = new OrderAdapter(OrderManagement.this, itemArrayList);
                ordersView.setAdapter(itemAdapter);

                ordersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                        intent.putExtra("order", orders.get(position));
                        startActivityForResult(intent, 104);
                    }
                });
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