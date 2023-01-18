package com.example.myapplication.activity;

import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.adapter.VoucherAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.components.ActionBar;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
//import com.example.myapplication.adapter.CategoryAdapter;
//import com.example.myapplication.components.FilterCategory;
import com.example.myapplication.content.Categories;
import com.example.myapplication.content.Vouchers;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.ColorTransparentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VoucherManagement extends BaseActivity {
    private ArrayList<Voucher> vouchers;
    private ListView categoryView;
    private ImageButton addButton;
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
    private Button cancelButton = new Button(R.id.cancelButton, this);
    private Button deleteButton = new Button(R.id.deleteButton, this);
    private RelativeLayout deleteNotification;
    private EditText searchBox;
    private ImageButton searchButton;
    private LinearLayout mask;
    private ImageButton logoutButton;
    private ImageButton viewFeedbackButton;
    private ProgressBar waitingForItemsDashboard;

    private View.OnClickListener logOutOnClickButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        };
    }

    private void setUpListViewVoucher(ArrayList<Voucher> vouchers){
        VoucherAdapter categoryAdapter = new VoucherAdapter(this, vouchers);
        categoryView.setAdapter(categoryAdapter);
    }

    private View.OnClickListener setViewFeedbackButtonOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewFeedbackActivity.class));
            }
        };
    }
    private void initUIComponent(){
        mask = findViewById(R.id.ll_mask);
        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);
        deleteNotification = findViewById(R.id.deleteNotification);
        addButton = findViewById(R.id.addButton);
        logoutButton = findViewById(R.id.logout);
        viewFeedbackButton = findViewById(R.id.viewFeedback);
        categoryView = findViewById(R.id.categoryList);
        waitingForItemsDashboard = findViewById(R.id.waiting_for_items_dashboard);
    }
    private void setUpButton(){
        addButton.setOnClickListener(onClickAddButton());
        cancelButton.createInactiveButton("Cancel", onClickCancelButton());
        deleteButton.createActiveButton("Yes, delete", onClickDeleteButton());
        viewFeedbackButton.setOnClickListener(setViewFeedbackButtonOnClick());
        logoutButton.setOnClickListener(logOutOnClickButton());
    }

    private void setUpBottomNavigation(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.vouchers);
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

    private void getAllVouchers() {
        (new APIHandler(VoucherManagement.this)).getRequest("/voucher/view", new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONArray jsonArray = response.getJSONArray("vouchers");
                ArrayList<Voucher> itemArrayList = new ArrayList<>();

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        System.out.println("object" + object);
                        itemArrayList.add(new Voucher(object.getString("_id"), object.getString("code"), object.getString("title"), object.getString("description"), object.getString("type"), object.getInt("value")));
                    }

                    setUpListViewVoucher(itemArrayList);
                    vouchers = itemArrayList;
                }

                stopLoading();
            }
        });
    }

    private void stopLoading() {
        waitingForItemsDashboard.setVisibility(View.GONE);
        categoryView.setVisibility(View.VISIBLE);
    }

    private void startLoading() {
        waitingForItemsDashboard.setVisibility(View.VISIBLE);
        categoryView.setVisibility(View.GONE);
    }

    private void setUpSearchBtn() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();

                ArrayList<Voucher> itemArrayList = new ArrayList<>();

                if (query.isEmpty()){
                    itemArrayList.addAll(vouchers);
                } else{
                    for (Voucher v : vouchers){
                        if (v.getCode().toLowerCase().contains(query.toLowerCase()) || v.getTitle().toLowerCase().contains(query.toLowerCase())){
                            itemArrayList.add(v);
                        }
                    }
                }

                VoucherAdapter itemAdapter = new VoucherAdapter(VoucherManagement.this, itemArrayList);
                categoryView.setAdapter(itemAdapter);
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
                mask.setVisibility(View.GONE);
                deleteNotification.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
            }
        };
    }

    private View.OnClickListener onClickDeleteButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mask.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
                deleteNotification.setVisibility(View.INVISIBLE);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_management);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        initUIComponent();
        getAllVouchers();
        setUpButton();
        setUpSearchBtn();
        setUpBottomNavigation();
        deleteNotification.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(R.color.black,70)));

        actionBar.createActionBar("Dashboard", R.drawable.logo_icon, 0);
    }
}