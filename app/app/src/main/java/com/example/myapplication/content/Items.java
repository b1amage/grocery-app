package com.example.myapplication.content;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.util.ArrayList;

public class Items {
    private ArrayList<Item> items = new ArrayList<>();
    public Items() {
        items.add(new Item(1, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(2, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(3, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(4, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(5, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(1, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(2, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(3, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(4, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
        items.add(new Item(5, "Fresh Cabbage", R.drawable.dummy_item, "Vegetables", 12));
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> categories) {
        this.items = categories;
    }
}
