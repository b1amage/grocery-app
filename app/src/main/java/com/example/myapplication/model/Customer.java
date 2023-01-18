package com.example.myapplication.model;

import java.io.Serializable;

public class Customer implements Serializable {
    private String _id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String role;

    public Customer(String _id, String username, String email, String phone, String role, String address) {
        this._id = _id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.address = address;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
