package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.CookieManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView listView;
    private List<Item> items;
    private TextView seeAllText;
    private TextView textName;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ImageButton buttonToAccount;
    private String nextCursor = "";
    private EditText searchBox;
    private ImageButton searchButton;

    private void initUIComponents() {
        listView = findViewById(R.id.item_listview);
        seeAllText = findViewById(R.id.see_all_txt);
        shimmerFrameLayout = findViewById(R.id.shimmer_main);
        textName = findViewById(R.id.textHello);
        buttonToAccount = findViewById(R.id.main_btn_to_account);
        searchBox = findViewById(R.id.home_search);
        searchButton = findViewById(R.id.main_btn_search);
    }

    private void setUpSearchBtn() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                if (query.isEmpty()) return;

                startLoading();

                (new APIHandler(MainActivity.this)).getRequest(String.format("/item/view?name=%s", query), new VolleyResponseListener() {
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

                            setUpListView(itemArrayList);
                            items = itemArrayList;
                        }

                        stopLoading();
                    }
                });
            }
        });
    }

    private void startLoading() {
        shimmerFrameLayout.startShimmer();
    }

    private void stopLoading() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    private void initContent() {
        String name = (new CookieManager(MainActivity.this)).getName();
        textName.setText(name.isEmpty() ? "Hello there!" : String.format("Hello, %s", name));
    }

    private void setUpListView(List<Item> itemList) {
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        listView.setAdapter(itemAdapter);

        // onClick: navigate to relevant item detail
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ItemDetail.class);
                intent.putExtra("_id", items.get(position).get_id());
                startActivityForResult(intent, 101);
            }
        });

        // onScroll: load more item if scroll to bottom
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                        listView.getFooterViewsCount()) >= (itemAdapter.getCount() - 1)) {
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

    }

    private void setUpButtonToAccount() {
        buttonToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivityForResult(intent, 102);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    System.out.println("Back from see all");
                    break;
                case 101:
                    System.out.println("Back from detail");
                    break;
                default:
                    System.out.println("Other");
            }
        }
    }

    private void setViewAllTextListener() {
        seeAllText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewAllItemActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void getAllItems() {
        (new APIHandler(MainActivity.this)).getRequest("/item/view", new VolleyResponseListener() {
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

                    setUpListView(itemArrayList);
                    items = itemArrayList;
                }

                stopLoading();
            }
        });
    }

    private void loadMoreItems() {
        (new APIHandler(MainActivity.this)).getRequest(String.format("/item/view?next_cursor=%s", nextCursor), new VolleyResponseListener() {
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

                    setUpListView(items);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();
        initContent();
        startLoading();
        setUpSearchBtn();
        setViewAllTextListener();
        setUpButtonToAccount();
        getAllItems();

    }
}