package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String _id;
    private Customer customer;
    private List<OrderItem> orderItems;
    private int subTotal;
    private int convertedPoints;
    private List<String> voucherApplied;
    private int discount;
    private int total;
    private boolean isFulfilled;

    public Order(String _id, Customer customer, List<OrderItem> orderItems, int subTotal, int convertedPoints, List<String> voucherApplied, int discount, int total, boolean isFulfilled) {
        this._id = _id;
        this.customer = customer;
        this.orderItems = orderItems;
        this.subTotal = subTotal;
        this.convertedPoints = convertedPoints;
        this.voucherApplied = voucherApplied;
        this.discount = discount;
        this.total = total;
        this.isFulfilled = isFulfilled;
    }

    public Order(List<OrderItem> orderItems, int convertedPoints, List<String> voucherApplied) {
        this.orderItems = orderItems;
        this.convertedPoints = convertedPoints;
        this.voucherApplied = voucherApplied;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getConvertedPoints() {
        return convertedPoints;
    }

    public void setConvertedPoints(int convertedPoints) {
        this.convertedPoints = convertedPoints;
    }

    public List<String> getVoucherApplied() {
        return voucherApplied;
    }

    public void setVoucherApplied(List<String> voucherApplied) {
        this.voucherApplied = voucherApplied;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        isFulfilled = fulfilled;
    }
}
