package com.example.myapplication.content;

import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderItem;
import com.example.myapplication.model.Voucher;

import java.util.ArrayList;

public class Orders {
    private ArrayList<Order> orders;

    public Orders() {
        orders = new ArrayList<>();
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 100, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus2", new ArrayList<OrderItem>(), 150, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus3", new ArrayList<OrderItem>(), 220, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus4", new ArrayList<OrderItem>(), 300, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus5", new ArrayList<OrderItem>(), 400, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus6", new ArrayList<OrderItem>(), 50, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus7", new ArrayList<OrderItem>(), 1000, 0, new ArrayList<String>(), 0, 100, true));

    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
