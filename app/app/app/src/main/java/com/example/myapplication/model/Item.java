package com.example.myapplication.model;

public class Item {
    private int id;
    private String name;
    private int img;
    private String category;
    private double price;

    public Item(int id, String name, int img, String category, double price) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img=" + img +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
