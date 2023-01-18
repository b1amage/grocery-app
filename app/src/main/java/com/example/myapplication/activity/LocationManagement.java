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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.LocationAdapter;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.adapter.VoucherAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.components.ActionBar;
//import com.example.myapplication.components.FilterCategory;
import com.example.myapplication.content.Categories;
import com.example.myapplication.content.Locations;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Location;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.ColorTransparentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationManagement extends BaseActivity {
    private ArrayList<Location> locations = new ArrayList<>();
//    private String[] categories = new Categories().getLocations();
    private ListView locationsView;
    private ImageButton addButton;
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
//    private FilterCategory filterCategory = new FilterCategory(categories, this, R.layout.category_item);
    private Button cancelButton = new Button(R.id.cancelButton, this);
    private Button deleteButton = new Button(R.id.deleteButton, this);
    private RelativeLayout deleteNotification;

    private EditText searchBox;
    private ImageButton searchButton;

    private LinearLayout mask;
    private ProgressBar waitingForItemsDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_management);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mask = findViewById(R.id.ll_mask);
        waitingForItemsDashboard = findViewById(R.id.waiting_for_items_dashboard);

        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);

        getAllItems();
        setUpSearchBtn();

        ImageButton logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });

        deleteNotification = findViewById(R.id.deleteNotification);
        deleteNotification.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(R.color.black,70)));

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(onClickAddButton());
        actionBar.createActionBar("Dashboard", R.drawable.logo_icon, 0);

        locationsView = findViewById(R.id.categoryList);
        setUpListViewLocation(locations);

        locationsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location location = (Location) locationsView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), StoreMapLocation.class);
                intent.putExtra("location", location);
                System.out.println(location);
                startActivity(intent);
            }
        });

//        filterCategory.selectCategory();
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
        bottomNavigationView.setSelectedItemId(R.id.store);
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

    private void getAllItems() {
        (new APIHandler(LocationManagement.this)).getRequest("/location/view", new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONArray jsonArray = response.getJSONArray("locations");
                ArrayList<Location> itemArrayList = new ArrayList<>();

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        System.out.println("object" + object);
                        itemArrayList.add(new Location(object.getString("_id"), object.getString("address"), object.getDouble("latitude"), object.getDouble("longitude")));
                    }

                    setUpListViewLocation(itemArrayList);
                    locations = itemArrayList;
                }

                stopLoading();
            }
        });
    }

    private void setUpListViewLocation(ArrayList<Location> locations){
        LocationAdapter categoryAdapter = new LocationAdapter(this, locations);
        locationsView.setAdapter(categoryAdapter);
    }

    private void stopLoading() {
        waitingForItemsDashboard.setVisibility(View.GONE);
        locationsView.setVisibility(View.VISIBLE);
    }

    private void startLoading() {
        waitingForItemsDashboard.setVisibility(View.VISIBLE);
        locationsView.setVisibility(View.GONE);
    }

    private void setUpSearchBtn() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();

                ArrayList<Location> itemArrayList = new ArrayList<>();

                if (query.isEmpty()){
                    itemArrayList.addAll(locations);
                } else{
                    for (Location l : locations){
                        if (l.getAddress().toLowerCase().contains(query.toLowerCase())){
                            itemArrayList.add(l);
                        }
                    }
                }

                LocationAdapter itemAdapter = new LocationAdapter(LocationManagement.this, itemArrayList);
                locationsView.setAdapter(itemAdapter);

                locationsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), StoreMapLocation.class);
                        intent.putExtra("location", locations.get(position));
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
}