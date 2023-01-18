package com.example.myapplication.model;

public class OrderItem {
    private String orderItemId;
    private int quantity;
    private Item item;

    public OrderItem(String orderItemId, int quantity, Item item) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.item = item;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
