package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.CustomSpinner;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener{

    private CustomSpinner spinner;
    private CategoryAdapter categoryAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ListView listView;
    private List<Item> items;
    private String nextCursor = "";
    private String categorySelected = "dairy";

    private void initUIComponents() {
        spinner = findViewById(R.id.filter_spinner);
        shimmerFrameLayout = findViewById(R.id.shimmer_filter);
        listView = findViewById(R.id.filter_list_view);
    }


    private void setUpListView(List<Item> itemList) {
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FilterActivity.this, ItemDetail.class);
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

    private void getAllItems(String endpoint) {
        (new APIHandler(FilterActivity.this)).getRequest(endpoint, new VolleyResponseListener() {
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
        (new APIHandler(FilterActivity.this)).getRequest(String.format("/item/view?category=%s&&next_cursor=%s", categorySelected, nextCursor), new VolleyResponseListener() {
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

                    setUpListView(items);
                }
            }
        });
    }

    private void setUpSpinner() {
        spinner.setSpinnerEventsListener(this);
        categoryAdapter = new CategoryAdapter(this);
        spinner.setAdapter(categoryAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FilterActivity.this, ((Category) spinner.getSelectedItem()).getCategoryName(), Toast.LENGTH_SHORT).show();
                getAllItems(String.format("/item/view?category=%s", ((Category) spinner.getSelectedItem()).getCategoryName()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initUIComponents();
        shimmerFrameLayout.startShimmer();
        setUpSpinner();

    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        spinner.setBackground(getResources().getDrawable(R.drawable.bg_spinner));
    }
}