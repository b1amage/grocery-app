package com.example.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemAdapter extends BaseAdapter {

    private List<Item> items;

    public CartItemAdapter(List<Item> items) {
        this.items = items;
    }

    public void updateResults(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
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
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View itemView;
        if (convertView == null) {
            itemView = View.inflate(parent.getContext(), R.layout.cart_item, null);
        } else itemView = convertView;

        Item item = (Item) getItem(i);

            ImageLoader.loadImg(item.getImageURL(), itemView.findViewById(R.id.cart_item_img));
            ((TextView) itemView.findViewById(R.id.cart_item_name)).setText(item.getName());
            ((TextView) itemView.findViewById(R.id.cart_item_category)).setText(item.getCategory());
            ((TextView) itemView.findViewById(R.id.cart_item_price)).setText(String.valueOf(item.getPrice()));

        return itemView;
    }
}
