package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.activity.LocationManagement;
import com.example.myapplication.activity.OrderDetailsActivity;
import com.example.myapplication.activity.OrderManagement;
import com.example.myapplication.activity.SignInActivity;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Voucher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {

    private ImageButton deleteButton;
    private RelativeLayout deleteNotification;
    private LinearLayout mask;

    public OrderAdapter(@NonNull Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
        deleteNotification = ((Activity) context).findViewById(R.id.deleteNotification);
        mask = ((Activity) context).findViewById(R.id.ll_mask);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.order_card, parent, false);
        }

        mask.setVisibility(View.INVISIBLE);

        Order order = (Order) getItem(position);

        ((TextView) listItemView.findViewById(R.id.orderTitle)).setText(order.getCustomer().getUsername());
        ((TextView) listItemView.findViewById(R.id.description)).setVisibility(View.VISIBLE);
        ((TextView) listItemView.findViewById(R.id.description)).setText("ID: " + order.get_id());
        ((TextView) listItemView.findViewById(R.id.orderPrice)).setText(order.getTotal() + " " + "VND");

        deleteButton = listItemView.findViewById(R.id.deleteButton);
        deleteButton.setImageResource(R.drawable.small_edit);
        deleteButton.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.primary_100));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                intent.putExtra("order", order);
                System.out.println(order);
                getContext().startActivity(intent);
            }
        });

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                intent.putExtra("order", order);
                System.out.println(order);
                getContext().startActivity(intent);
            }
        });
        return listItemView;
    }

}
