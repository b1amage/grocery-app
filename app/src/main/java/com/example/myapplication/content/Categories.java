package com.example.myapplication.content;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;

import java.util.ArrayList;

public class Categories {
    private String[] categories;
    public Categories() {
        this.categories = new String[]{"Vegetables", "Meat", "Fruit", "Diary", "Canned", "Snack", "Drinks", "Spice", "Household", "Personal hygiene"};
    }
    public String[] getCategories(){
        return categories;
    }
}
