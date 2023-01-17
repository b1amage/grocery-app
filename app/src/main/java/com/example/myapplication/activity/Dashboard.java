package com.example.myapplication.activity;

import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.CategoryItemAdapter;
import com.example.myapplication.adapter.ItemAdapter;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
//import com.example.myapplication.adapter.CategoryAdapter;
//import com.example.myapplication.components.FilterCategory;
import com.example.myapplication.content.Categories;
import com.example.myapplication.content.Items;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.ColorTransparentUtils;
import com.example.myapplication.utilities.CustomSpinner;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Dashboard extends BaseActivity implements CustomSpinner.OnSpinnerEventsListener{
//    private String[] categories = new Categories().getCategories();
    private ListView categoryView;
    private ImageButton addButton;
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
//    private FilterCategory filterCategory = new FilterCategory(categories, this, R.layout.category_item);
    private Button cancelButton = new Button(R.id.cancelButton, this);
    private Button deleteButton = new Button(R.id.deleteButton, this);
    private RelativeLayout deleteNotification;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Item> items = Items.getItems();
    private CustomSpinner spinner;
    private String categorySelected = "";
    private EditText searchBox;
    private ImageButton searchButton;
    private LinearLayout mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mask = findViewById(R.id.ll_mask);

        ImageButton viewFeedbackButton = findViewById(R.id.viewFeedback);
        viewFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewFeedbackActivity.class));
            }
        });

        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);
        setUpSearchBtn();

        spinner = findViewById(R.id.filter_spinner);

        spinner.setSpinnerEventsListener(this);
        categoryAdapter = new CategoryAdapter(this);
        spinner.setAdapter(categoryAdapter);

        categoryView = findViewById(R.id.categoryList);
        CategoryItemAdapter categoryAdapter = new CategoryItemAdapter(this, items);
        categoryView.setAdapter(categoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(FilterActivity.this, ((Category) spinner.getSelectedItem()).getCategoryName(), Toast.LENGTH_SHORT).show();
//                getAllItems(String.format("/item/view?category=%s", ((Category) spinner.getSelectedItem()).getCategoryName()));
                ArrayList<Item> itemsList = new ArrayList<>();
                categorySelected = ((Category) spinner.getSelectedItem()).getCategoryName();
                if (categorySelected.isEmpty()){
                    itemsList.addAll(items);
                } else {
                    for (Item item : items){
                        if (item.getCategory().toLowerCase().contains(categorySelected)){
                            itemsList.add(item);
                        }
                    }
                }
                CategoryItemAdapter itemAdapter = new CategoryItemAdapter(Dashboard.this, itemsList);
                categoryView.setAdapter(itemAdapter);

                categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
                        intent.putExtra("_id", items.get(position).get_id());
                        startActivityForResult(intent, 104);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        cancelButton.createInactiveButton("Cancel", onClickCancelButton());
        deleteButton.createActiveButton("Yes, delete", onClickDeleteButton());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.items);
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

                ArrayList<Item> itemArrayList = new ArrayList<>();

                if (query.isEmpty()){
                    itemArrayList.addAll(items);
                } else{
                    for (Item item : items){
                        if (item.getName().toLowerCase().contains(query.toLowerCase())){
                            itemArrayList.add(item);
                        }
                    }
                }

                CategoryItemAdapter itemAdapter = new CategoryItemAdapter(Dashboard.this, itemArrayList);
                categoryView.setAdapter(itemAdapter);

                categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
                        intent.putExtra("_id", items.get(position).get_id());
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

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
//        spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
//        spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner));
    }
}