package com.example.myapplication.activity;

import com.example.myapplication.adapter.OrderItemAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.components.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderItem;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.CookieManager;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderDetailsActivity extends AppCompatActivity {
    private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ActionBar actionBarOrderDetails = new ActionBar(R.id.orderDetailsActionBar, this);
        Button completeOrderButton = new Button(R.id.completeOrderButton, this);

        actionBarOrderDetails.createActionBar("Order details", R.drawable.ic_back, R.drawable.navbutton_shape);

        String role = (new CookieManager(getApplicationContext())).getRole();
        if (role.equals("customer")) {
            completeOrderButton.createInactiveButton("Cancel",onClickCancelOrder());
        } else {
            completeOrderButton.createInactiveButton("Complete",onClickCompleteOrder());
        }

        order = (Order) getIntent().getSerializableExtra("order");

        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(getApplicationContext(), order.getOrderItems());
        ListView orderItemListView = findViewById(R.id.orderItemList);
        orderItemListView.setAdapter(orderItemAdapter);

        TextView orderId = findViewById(R.id.orderId);
        TextView customerId = findViewById(R.id.customerId);
        TextView phone = findViewById(R.id.customerPhone);
        TextView email = findViewById(R.id.customerEmail);
        TextView address = findViewById(R.id.customerAddress);

        TextView subTotalValue = findViewById(R.id.subTotalValue);
        TextView discountValue = findViewById(R.id.discountValue);
        TextView totalValue = findViewById(R.id.totalValue);

        System.out.println(order);

        orderId.setText(order.get_id());
        customerId.setText(order.getCustomer().getUsername());
        phone.setText(order.getCustomer().getPhone());
        email.setText(order.getCustomer().getEmail());
        address.setText(order.getCustomer().getAddress());
        subTotalValue.setText(order.getSubTotal() + " VND");
        discountValue.setText(order.getDiscount() + " VND");
        totalValue.setText(order.getTotal() + " VND");

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
                JSONObject orderObject = new JSONObject();
                try {
                    orderObject.put("order", order);
                    (new APIHandler(OrderDetailsActivity.this)).updateRequest(orderObject,"/order/fulfill/" + order.get_id(), new VolleyResponseListener() {
                        @Override
                        public void onError(String message, int statusCode) {
                            System.err.println(message);
                            startActivity(new Intent(OrderDetailsActivity.this, SignInActivity.class));
                        }

                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            System.out.println(response);
                            startActivity(new Intent(OrderDetailsActivity.this, OrderManagement.class));
                        }

                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}