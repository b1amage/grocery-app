package com.example.myapplication.activity;

import com.example.myapplication.adapter.OrderItemAdapter;
import com.example.myapplication.components.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.OrderItem;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.CookieManager;

public class OrderDetailsActivity extends AppCompatActivity {

    OrderItem[] orders = new OrderItem[]{ new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2), new OrderItem("Items", 2)};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ActionBar actionBarOrderDetails = new ActionBar(R.id.orderDetailsActionBar, this);
        Button completeOrderButton = new Button(R.id.completeOrderButton, this);

        actionBarOrderDetails.createActionBar("Order details", R.drawable.ic_back, R.drawable.navbutton_shape);

        String role = (new CookieManager(getApplicationContext())).getRole();
        if (role.equals("customer")) {
            completeOrderButton.createInactiveButton("Cancel", onClickCancelOrder());
        } else {
            completeOrderButton.createInactiveButton("Complete", onClickCompleteOrder());
        }

        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(getApplicationContext(), orders);
        ListView orderItemListView = findViewById(R.id.orderItemList);
        orderItemListView.setAdapter(orderItemAdapter);

    }

    private View.OnClickListener onClickCancelOrder(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cancel Order", Toast.LENGTH_LONG).show();
            }
        };
    }

    private View.OnClickListener onClickCompleteOrder(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Complete Order", Toast.LENGTH_LONG).show();
            }
        };
    }
}