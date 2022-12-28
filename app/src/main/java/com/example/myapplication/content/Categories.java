package com.example.myapplication.content;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;

import java.util.ArrayList;

public class Categories {
    private ArrayList<Category> categories = new ArrayList<>();
    public Categories() {
        categories.add(new Category(R.drawable.ic_back, "Category"));
        categories.add(new Category(R.drawable.ic_back, "Category"));
        categories.add(new Category(R.drawable.ic_back, "Category"));
        categories.add(new Category(R.drawable.ic_back, "Category"));
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
