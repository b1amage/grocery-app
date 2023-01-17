package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.activity.OrderDetailsActivity;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.Voucher;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    private ImageView edit;
    private LinearLayout deleteLayout;
//    private RelativeLayout deleteNotification;

    public OrderAdapter(@NonNull Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
//        deleteNotification = ((Activity) context).findViewById(R.id.deleteNotification);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_card, parent, false);
        }

        Order order = (Order) getItem(position);

        ((ImageView) listItemView.findViewById(R.id.itemImage)).setImageResource(R.drawable.orders);
        ((TextView) listItemView.findViewById(R.id.itemName)).setText(order.get_id());
        ((TextView) listItemView.findViewById(R.id.itemInfo)).setVisibility(View.GONE);
        ((TextView) listItemView.findViewById(R.id.itemPrice)).setText(order.getTotal() + " " + "VND");

        edit = listItemView.findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                intent.putExtra("order", order);
                getContext().startActivity(intent);
            }
        });

        deleteLayout = listItemView.findViewById(R.id.delete);
        deleteLayout.setVisibility(View.GONE);

        return listItemView;
    }

}
