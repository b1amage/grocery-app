package com.example.myapplication.content;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;

import java.util.ArrayList;

public class Categories {
    private ArrayList<Category> categories = new ArrayList<>();
    public Categories() {
        categories.add(new Category(R.drawable.food, "Food"));
        categories.add(new Category(R.drawable.tech, "Technology"));
        categories.add(new Category(R.drawable.furniture, "Furniture "));
        categories.add(new Category(R.drawable.clothes, "Clothes"));
        categories.add(new Category(R.drawable.voucher, "Voucher"));
        categories.add(new Category(R.drawable.logo_icon, "Information"));
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
