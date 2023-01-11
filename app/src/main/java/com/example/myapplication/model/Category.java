package com.example.myapplication.model;

public class Category {
    private int iconId;
    private String categoryName;
    private int img;

    public Category(int iconId, String categoryName) {
        this.iconId = iconId;
        this.categoryName = categoryName;
    }

    public Category(int iconId, String categoryName, int img) {
        this.iconId = iconId;
        this.categoryName = categoryName;
        this.img = img;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Category{" +
                "iconId=" + iconId +
                ", categoryName='" + categoryName + '\'' +
                ", img=" + img +
                '}';
    }
}
