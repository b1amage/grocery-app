package com.example.myapplication.content;

import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderItem;
import com.example.myapplication.model.Voucher;

import java.util.ArrayList;

public class Orders {
    private ArrayList<Order> orders;

    public Orders() {
        orders = new ArrayList<>();
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 0, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 0, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 0, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 0, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 0, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 0, 0, new ArrayList<String>(), 0, 100, true));
        this.orders.add(new Order("abcd", "cus1", new ArrayList<OrderItem>(), 0, 0, new ArrayList<String>(), 0, 100, true));

    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
