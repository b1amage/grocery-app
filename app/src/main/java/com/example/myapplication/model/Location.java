package com.example.myapplication.model;

public class Location {
    private String _id;
    private String address;
    private double latitude;
    private double longitude;

    public Location(String _id, String address, double latitude, double longitude) {
        this._id = _id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}