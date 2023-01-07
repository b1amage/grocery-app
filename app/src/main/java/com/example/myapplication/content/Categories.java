package com.example.myapplication.content;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;

import java.util.ArrayList;

public class Categories {
    private String[] categories;
    private String[] voucherTypes;
    private String[] times;
    private String[] locations;

    public Categories() {
        this.categories = new String[]{"Vegetables", "Meat", "Fruit", "Diary", "Canned", "Snack", "Drinks", "Spice", "Household", "Personal hygiene"};
        this.voucherTypes = new String[]{"Percentage", "VND"};
        this.times = new String[]{"Newest", "Oldest"};
        this.locations = new String[]{};
    }
    public String[] getCategories(){
        return categories;
    }

    public String[] getVoucherTypes(){
        return voucherTypes;
    }

    public String[] getTimes() {
        return times;
    }

    public String[] getLocations(){
        return locations;
    }
}
