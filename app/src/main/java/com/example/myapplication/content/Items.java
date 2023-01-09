package com.example.myapplication.content;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.util.ArrayList;

public class Items {
    private ArrayList<Item> items = new ArrayList<>();
    public Items() {
        items.add(new Item("6d3", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d1", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d2", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d4", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d5", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d6", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d7", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d8", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
        items.add(new Item("6d9", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "", 12));
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> categories) {
        this.items = categories;
    }
}
