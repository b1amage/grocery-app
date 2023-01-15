package com.example.myapplication.model;

public class Feedback {
    private String _id;
    private String email;
    private String title;
    private String description;

    public Feedback(String _id, String email, String title, String description) {
        this._id = _id;
        this.email = email;
        this.title = title;
        this.description = description;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
