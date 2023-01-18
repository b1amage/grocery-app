package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartItemAdapter;
import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.content.Items;
import com.example.myapplication.db.DBManager;
import com.example.myapplication.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartActivity extends BaseActivity {
    private ListView listView;
    private ImageButton backBtn;
    private List<Item> items;
    private ImageButton minusBtn;
    private ImageButton addBtn;
    private ItemAdapter itemAdapter;
    private TextView quantity;
    private EditText voucherEdt;
    private EditText pointEdt;
    private Button orderBtn;
    private TextView prePrice;

    private void initUIComponents() {
        listView = findViewById(R.id.cart_list_view);
        backBtn = findViewById(R.id.cart_back_btn);
        minusBtn = findViewById(R.id.cart_item_minus_btn);
        addBtn = findViewById(R.id.cart_item_plus_btn);
        quantity = findViewById(R.id.cart_item_quantity);
        voucherEdt = findViewById(R.id.voucher_edt);
        pointEdt = findViewById(R.id.point_edt);
        orderBtn = findViewById(R.id.order_btn);
        prePrice = findViewById(R.id.preview_price);
    }

    private void calPrePrice() {
        int subTotal = (new DBManager(CartActivity.this)).getSubTotal();
        prePrice.setText("Subtotal: " + subTotal);
    }

    private void setUpOrderBtn() {
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String voucher = voucherEdt.getText().toString();
                List<String> vouchers;

                if (!voucher.isEmpty()) {
                    vouchers = new ArrayList<>(Arrays.asList(voucher));
                } else {
                    vouchers = new ArrayList<>();
                }

                int point = Integer.parseInt(pointEdt.getText().toString());

                if (point < 0) return;

                JSONArray orderItems = new JSONArray();

                for (Item item : items) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("item", item.get_id());
                        jsonObject.put("quantity", item.getQuantity());
                        orderItems.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                JSONObject postData = new JSONObject();
                try {
                    postData.put("orderItems", orderItems);
                    postData.put("convertedPoints", point);
                    postData.put("voucherApplied", new JSONArray(vouchers));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                (new APIHandler(CartActivity.this)).postRequest(postData, "/order/create", new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        System.err.println(message);
                        Intent intent = new Intent(CartActivity.this, ErrorActivity.class);
                        intent.putExtra("error", message);
                        startActivity(intent);
                    }

                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        System.out.println(response);
                        int subTotal = response.getJSONObject("order").getInt("subTotal");
                        int discount = response.getJSONObject("order").getInt("discount");
                        int total = response.getJSONObject("order").getInt("total");
                    }
                });
            }
        });
    }

    private void getCartItem() {
        System.out.println("Get cart called");
        items = (new DBManager(CartActivity.this)).fetchItemsFromCart();
        System.out.println(items);
    }


    private void setUpListView(List<Item> itemList) {
        System.out.println(itemList);
        itemAdapter = new ItemAdapter(itemList, true, CartActivity.this, prePrice);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Calling set on item");
                Toast.makeText(CartActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setBackButtonListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initUIComponents();
        getCartItem();
        setUpOrderBtn();
        setUpListView(items);
        setBackButtonListener();
    }


}