package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ItemCart.DB";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String TABLE_NAME = "ItemCart";
    public static final String _id = "_id";
    public static final String IMAGE_URL = "imageURL";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String CATEGORY = "category";
    public static final String ORDER_QUANTITY = "order_quantity";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _id + " TEXT PRIMARY KEY, "
            + IMAGE_URL + " TEXT NOT NULL,"
            + NAME + " TEXT, "
            + PRICE + " TEXT, "
            + CATEGORY + " TEXT, "
            + ORDER_QUANTITY + " INTEGER"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}