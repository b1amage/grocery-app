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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    private ListView categoryView;
    private ImageButton addButton;
    private ActionBar actionBar = new ActionBar(R.id.actionBar, this);
    private ImageButton viewFeedbackButton;
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
    private ImageButton logoutButton;
    private BottomNavigationView bottomNavigationView;
    private String nextCursor = "";
    private CategoryItemAdapter categoryItemAdapter;
    private ProgressBar waitingForItemsDashboard;

    private void initUIComponents() {
        mask = findViewById(R.id.ll_mask);
        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);
        spinner = findViewById(R.id.filter_spinner);
        categoryView = findViewById(R.id.categoryList);
        categoryView.setVisibility(View.GONE);
        deleteNotification = findViewById(R.id.deleteNotification);
        viewFeedbackButton = findViewById(R.id.viewFeedback);
        addButton = findViewById(R.id.addButton);
        logoutButton = findViewById(R.id.logout);
        bottomNavigationView = findViewById(R.id.bottomNav);

        deleteNotification.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(R.color.black,70)));
        actionBar.createActionBar("Dashboard", R.drawable.logo_icon, 0);

        waitingForItemsDashboard = findViewById(R.id.waiting_for_items_dashboard);
    }


    private void setUpButton(){
        addButton.setOnClickListener(onClickAddButton());
        cancelButton.createInactiveButton("Cancel", onClickCancelButton());
        deleteButton.createActiveButton("Yes, delete", onClickDeleteButton());
        viewFeedbackButton.setOnClickListener(viewFeedBackButtonOnClick());
    }

    private View.OnClickListener viewFeedBackButtonOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewFeedbackActivity.class));
            }
        };
    }

    private void setUpSpinner(){
        spinner.setSpinnerEventsListener(this);
        categoryAdapter = new CategoryAdapter(this);
        spinner.setAdapter(categoryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ArrayList<Item> itemsList = new ArrayList<>();
                categorySelected = ((Category) spinner.getSelectedItem()).getCategoryName();
//                if (categorySelected.isEmpty()){
//                    itemsList.addAll(items);
//                } else {
//                    for (Item item : items){
//                        if (item.getCategory().toLowerCase().contains(categorySelected)){
//                            itemsList.add(item);
//                        }
//                    }
//                }

//                itemAdapter = new CategoryItemAdapter(Dashboard.this, itemsList);
//                categoryView.setAdapter(itemAdapter);
                startLoading();
                if (categorySelected.isEmpty()) return;
                String endpoint = "/item/view";
                if (!categorySelected.equals("all")) {
                    endpoint = String.format("/item/view?category=%s", categorySelected);
                }
                (new APIHandler(Dashboard.this)).getRequest(endpoint, new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        System.out.println(message);
                    }

                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        System.out.println(response);
                        JSONArray jsonArray = response.getJSONArray("results");
                        nextCursor = response.getString("next_cursor");
                        ArrayList<Item> itemArrayList = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                System.out.println("object" + object);
                                itemArrayList.add(new Item(object.getString("_id"), object.getString("name"), "", object.getInt("price"), object.getString("category"), object.getString("image"), object.getInt("quantity")));
                            }

                            setUpListViewItem(itemArrayList);
                            items = itemArrayList;
                        }

                        stopLoading();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUpListViewItem(ArrayList<Item> itemList){
        categoryItemAdapter = new CategoryItemAdapter(this, itemList);
        categoryView.setAdapter(categoryItemAdapter);

        // onScroll: load more item if scroll to bottom
        categoryView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (categoryView.getLastVisiblePosition() - categoryView.getHeaderViewsCount() -
                        categoryView.getFooterViewsCount()) >= (categoryItemAdapter.getCount() - 1)) {
                    if (nextCursor != null) {
                        // load more
                        loadMoreItems();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
                intent.putExtra("_id", items.get(position).get_id());
                startActivityForResult(intent, 104);
            }
        });
    }

    private void setUpLogOutButton(){
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new APIHandler(Dashboard.this)).logoutRequest("/auth/logout", new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void setUpBottomNavigation(){
        bottomNavigationView.setSelectedItemId(R.id.items);
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

    private void stopLoading() {
        waitingForItemsDashboard.setVisibility(View.GONE);
        categoryView.setVisibility(View.VISIBLE);
    }

    private void startLoading() {
        waitingForItemsDashboard.setVisibility(View.VISIBLE);
        categoryView.setVisibility(View.GONE);
    }

    private void loadMoreItems() {
        (new APIHandler(Dashboard.this)).getRequest(String.format("/item/view?next_cursor=%s", nextCursor), new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                JSONArray jsonArray = response.getJSONArray("results");
                nextCursor = response.getString("next_cursor");

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        System.out.println("object" + object);
                        items.add(new Item(object.getString("_id"), object.getString("name"), "", object.getInt("price"), object.getString("category"), object.getString("image"), object.getInt("quantity")));
                    }

                    categoryItemAdapter.updateResults(items);
                }
            }
        });
    }

    private void setUpSearchBtn() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                if (query.isEmpty()) return;

                startLoading();

                (new APIHandler(Dashboard.this)).getRequest(String.format("/item/view?name=%s", query), new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        System.out.println(message);
                    }

                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        System.out.println(response);
                        JSONArray jsonArray = response.getJSONArray("results");
                        nextCursor = response.getString("next_cursor");
                        ArrayList<Item> itemArrayList = new ArrayList<>();

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                System.out.println("object" + object);
                                itemArrayList.add(new Item(object.getString("_id"), object.getString("name"), "", object.getInt("price"), object.getString("category"), object.getString("image"), object.getInt("quantity")));
                            }

                            setUpListViewItem(itemArrayList);
                            items = itemArrayList;
                        }

                        stopLoading();
                    }
                });
            }
        });
    }

    private void getAllItems() {
        (new APIHandler(Dashboard.this)).getRequest("/item/view", new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                System.out.println(response);
                JSONArray jsonArray = response.getJSONArray("results");
                nextCursor = response.getString("next_cursor");
                ArrayList<Item> itemArrayList = new ArrayList<>();

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        System.out.println("object" + object);
                        itemArrayList.add(new Item(object.getString("_id"), object.getString("name"), "", object.getInt("price"), object.getString("category"), object.getString("image"), object.getInt("quantity")));
                    }

                    setUpListViewItem(itemArrayList);
                    items = itemArrayList;
                }

                stopLoading();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        initUIComponents();
        setUpButton();
        setUpSpinner();
        setUpSearchBtn();
        setUpLogOutButton();
        setUpBottomNavigation();
        getAllItems();
    }
}