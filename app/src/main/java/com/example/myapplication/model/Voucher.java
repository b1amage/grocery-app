package com.example.myapplication.model;

public class Voucher {
    private String _id;
    private String code;
    private String title;
    private String description;
    private String type;
    private int value;

    public Voucher(String _id, String code, String title, String description, String type, int value) {
        this._id = _id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public Voucher(String code, String title, String description, String type, int value) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
