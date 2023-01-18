package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class OrderInfoActivity extends AppCompatActivity {

    private TextView subTotal;
    private TextView discount;
    private TextView total;
    private Button btnOk;

    private void initUIComponents() {
        subTotal = findViewById(R.id.info_subtotal);
        discount = findViewById(R.id.info_discount);
        total = findViewById(R.id.info_total);
        btnOk = findViewById(R.id.info_ok_btn);
    }

    private void setUpBtn() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInfoActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpTexts() {
        int subTotalInt = getIntent().getExtras().getInt("subTotal");
        int totalInt = getIntent().getExtras().getInt("total");
        int discountInt = getIntent().getExtras().getInt("discount");

        subTotal.setText(subTotalInt + " VND");
        total.setText(totalInt + " VND");
        discount.setText(discountInt + " VND");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        initUIComponents();
        setUpBtn();
        setUpTexts();
    }
}