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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Customer;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderItem;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.ColorTransparentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderManagement extends BaseActivity {
    private ArrayList<Order> orders;
    private ListView ordersView;
    private ImageButton addButton;
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
//    private FilterCategory filterCategory = new FilterCategory(categories, this, R.layout.category_item);
    private Button cancelButton = new Button(R.id.cancelButton, this);
    private Button deleteButton = new Button(R.id.deleteButton, this);
    private RelativeLayout deleteNotification;

    private EditText searchBox;
    private ImageButton searchButton;

    private ProgressBar waitingForItemsDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        waitingForItemsDashboard = findViewById(R.id.waiting_for_items_dashboard);

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

        getAllOrders();

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
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.vouchers:
                        startActivity(new Intent(getApplicationContext(), VoucherManagement.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.orders:
                        startActivity(new Intent(getApplicationContext(), OrderManagement.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.store:
                        startActivity(new Intent(getApplicationContext(), LocationManagement.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }


    private void getAllOrders() {
        (new APIHandler(OrderManagement.this)).getRequest("/order/view?isFulfilled=false", new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONArray jsonArray = response.getJSONArray("orders");
                ArrayList<Order> itemArrayList = new ArrayList<>();

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        JSONObject customerObject = object.getJSONObject("customer");
                        JSONArray orderItems = object.getJSONArray("orderItems");
                        JSONArray vouchersApplied = object.getJSONArray("voucherApplied");

                        Customer customer = new Customer(customerObject.getString("_id"), customerObject.getString("username"), customerObject.getString("email"), customerObject.getString("phone"), customerObject.getString("role"), customerObject.getString("address"));
                        List<OrderItem>  orderItemList = new ArrayList<>();
                        List<String> voucherAppliedList = new ArrayList<>();

                        for (int j = 0; j < orderItems.length(); j++){
                            JSONObject orderItemObject = orderItems.getJSONObject(j);
                            JSONObject itemObject = orderItemObject.getJSONObject("item");

                            Item item = new Item(itemObject.getString("_id"), itemObject.getString("name"), "", itemObject.getInt("price"), itemObject.getString("category"), itemObject.getString("image"), itemObject.getInt("quantity"));
                            orderItemList.add(new OrderItem(orderItemObject.getString("_id"), orderItemObject.getInt("quantity"), item));
                        }

                        for (int j = 0; j < vouchersApplied.length(); j++){
                            voucherAppliedList.add(vouchersApplied.getString(j));
                        }

                        System.out.println("object" + object);
                        itemArrayList.add(new Order(object.getString("_id"), customer, orderItemList, object.getInt("subTotal"), object.getInt("convertedPoints"), voucherAppliedList, object.getInt("discount"), object.getInt("total"), object.getBoolean("isFulfilled")));
                    }

                    setUpListViewOrders(itemArrayList);
                    orders = itemArrayList;
                }

                stopLoading();
            }
        });
    }

    private void setUpListViewOrders(ArrayList<Order> orders){
        OrderAdapter categoryAdapter = new OrderAdapter(this, orders);
        ordersView.setAdapter(categoryAdapter);
    }

    private void stopLoading() {
        waitingForItemsDashboard.setVisibility(View.GONE);
        ordersView.setVisibility(View.VISIBLE);
    }

    private void startLoading() {
        waitingForItemsDashboard.setVisibility(View.VISIBLE);
        ordersView.setVisibility(View.GONE);
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