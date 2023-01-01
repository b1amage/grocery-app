package com.example.myapplication.model;

public class Category {
    private int iconId;
    private String categoryName;

    public Category(int iconId, String categoryName) {
        this.iconId = iconId;
        this.categoryName = categoryName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
