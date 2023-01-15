package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderItem;

import java.util.List;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> {

    public OrderItemAdapter(@NonNull Context context, @NonNull List<OrderItem> orderItems) {
        super(context, 0, orderItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.order_item, parent, false);
        }

        OrderItem orderItem = (OrderItem) getItem(position);

        ((TextView) listItemView.findViewById(R.id.orderItemName)).setText(orderItem.getItemId());
        ((TextView) listItemView.findViewById(R.id.orderItemQuantity)).setText("x" + String.valueOf(orderItem.getQuantity()));

        return listItemView;
    }
}
