package com.example.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View itemView;
        if (convertView == null) {
            itemView = View.inflate(parent.getContext(), R.layout.item, null);
        } else itemView = convertView;

        Item item = (Item) getItem(i);

        ((ImageView) itemView.findViewById(R.id.item_img)).setImageResource(item.getImg());
        ((TextView) itemView.findViewById(R.id.item_name)).setText(item.getName());
        ((TextView) itemView.findViewById(R.id.item_category)).setText(item.getCategory());
        ((TextView) itemView.findViewById(R.id.item_price)).setText("$" + String.valueOf(item.getPrice()));


        return itemView;
    }
}