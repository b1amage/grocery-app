package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ClientVoucherAdapter;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Voucher;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VoucherList extends AppCompatActivity {

    private ListView listView;
    private ClientVoucherAdapter voucherAdapter;
    private List<Voucher> vouchers;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ImageButton backBtn;

    private void initUIComponents() {
        listView = findViewById(R.id.voucher_list_view);
        shimmerFrameLayout = findViewById(R.id.shimmer_voucher);
        backBtn = findViewById(R.id.voucher_back_btn);
    }

    private void setBackButtonListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VoucherList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpListView(List<Voucher> voucherList) {
        voucherAdapter = new ClientVoucherAdapter(voucherList);
        listView.setAdapter(voucherAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // onScroll: load more item if scroll to bottom
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                        listView.getFooterViewsCount()) >= (voucherAdapter.getCount() - 1)) {
                        // load more
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });
    }

    private void getAllVouchers() {
        (new APIHandler(VoucherList.this)).getRequest("/voucher/view", new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                JSONArray jsonArray = response.getJSONArray("vouchers");
                ArrayList<Voucher> voucherArrayList = new ArrayList<>();

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        System.out.println("object" + object);
                        voucherArrayList.add(new Voucher(object.getString("_id"), object.getString("code"), object.getString("title"), object.getString("description"), object.getString("type"), object.getInt("value")));
                    }

                    vouchers = voucherArrayList;
                    setUpListView(voucherArrayList);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_list);
        initUIComponents();
        setBackButtonListener();
        shimmerFrameLayout.startShimmer();
        getAllVouchers();
    }
}