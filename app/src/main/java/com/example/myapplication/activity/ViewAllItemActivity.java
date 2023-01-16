package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Item;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewAllItemActivity extends BaseActivity {

    private ListView listView;
    private ImageButton backBtn;
    private ShimmerFrameLayout shimmerFrameLayout;
    private String nextCursor = "";
    private List<Item> items;
    private EditText searchBox;
    private ImageButton searchButton;
    ItemAdapter itemAdapter;
    private ImageButton filterButton;

    private void initUIComponents() {
        listView = findViewById(R.id.view_all_list_view);
        backBtn = findViewById(R.id.view_all_back_btn);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_all);
        searchBox = findViewById(R.id.view_all_search);
        searchButton = findViewById(R.id.view_all_btn_search);
        filterButton = findViewById(R.id.view_all_btn_filter);
    }

    private void setUpFilterButton() {
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllItemActivity.this, FilterActivity.class);
                startActivityForResult(intent, 201);
            }
        });
    }

    private void setUpSearchBtn() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchBox.getText().toString();
                if (query.isEmpty()) return;

                (new APIHandler(ViewAllItemActivity.this)).getRequest(String.format("/item/view?name=%s", query), new VolleyResponseListener() {
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
                                itemArrayList.add(new Item(object.getString("_id"), object.getString("name"), "", object.getInt("price"), object.getString("category"), object.getString("image"), object.getInt("quantity")));
                            }

                            setUpListView(itemArrayList);
                            items = itemArrayList;
                        }
                    }
                });
            }
        });
    }

    private void setUpListView(List<Item> itemList) {
        itemAdapter = new ItemAdapter(itemList);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewAllItemActivity.this, ItemDetail.class);
                intent.putExtra("_id", items.get(position).get_id());
                startActivityForResult(intent, 104);
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

    private void setBackButtonListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAllItemActivity.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void getAllItems() {
        (new APIHandler(ViewAllItemActivity.this)).getRequest("/item/view", new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                JSONArray jsonArray = response.getJSONArray("results");
                nextCursor = response.getString("next_cursor");
                ArrayList<Item> itemArrayList = new ArrayList<>();

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        System.out.println("object" + object);
                        itemArrayList.add(new Item(object.getString("_id"), object.getString("name"), "", object.getInt("price"), object.getString("category"), object.getString("image"), object.getInt("quantity")));
                    }

                    items = itemArrayList;
                    setUpListView(itemArrayList);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadMoreItems() {
        (new APIHandler(ViewAllItemActivity.this)).getRequest(String.format("/item/view?next_cursor=%s", nextCursor), new VolleyResponseListener() {
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
                        items.add(new Item(object.getString("_id"), object.getString("name"), "", object.getInt("price"), object.getString("category"), object.getString("image"), object.getInt("quantity")));
                    }

                    itemAdapter.updateResults(items);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_item);

        initUIComponents();
        shimmerFrameLayout.startShimmer();
        setBackButtonListener();
        setUpSearchBtn();
        getAllItems();
        setUpFilterButton();
    }
}