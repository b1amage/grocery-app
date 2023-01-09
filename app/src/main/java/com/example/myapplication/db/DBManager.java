package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.Item;

import java.util.ArrayList;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;
    private SQLiteDatabase database;
    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertItemToCart(Item item, int orderQuantity) {
        if (checkItemExistsInCart(item.get_id())) {
            return;
        }
        open();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._id, item.get_id());
        contentValue.put(DatabaseHelper.IMAGE_URL, item.getImageURL());
        contentValue.put(DatabaseHelper.NAME, item.getName());
        contentValue.put(DatabaseHelper.PRICE, item.getPrice());
        contentValue.put(DatabaseHelper.CATEGORY, item.getCategory());
        contentValue.put(DatabaseHelper.ORDER_QUANTITY, orderQuantity);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
        database.close();
    }

    public void deleteItemFromCart(Item item) {
        open();
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._id + "=?" , new String[]{item.get_id()});
        database.close();
    }

    public ArrayList<Item> fetchItemsFromCart() {
        open();
        ArrayList<Item> favoriteBooks = new ArrayList<>();
        String[] columns = new String[] {
                DatabaseHelper._id,
                DatabaseHelper.IMAGE_URL,
                DatabaseHelper.NAME,
                DatabaseHelper.PRICE,
                DatabaseHelper.CATEGORY,
                DatabaseHelper.ORDER_QUANTITY
        };

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String _id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper._id));
                String imageURL = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.IMAGE_URL));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PRICE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY));
                int orderQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_QUANTITY));

                favoriteBooks.add(new Item(_id, name, "", price, category, imageURL, orderQuantity));
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return favoriteBooks;
    }

    public void increaseItemQuantity(Item item) {
        open();
        String valueToIncrementBy = "1";
        String[] bindingArgs = new String[]{ valueToIncrementBy, item.get_id() };

        database.execSQL("UPDATE " + DatabaseHelper.TABLE_NAME +
                        " SET " + DatabaseHelper.ORDER_QUANTITY + " = " + DatabaseHelper.ORDER_QUANTITY + " + ?" +
                        " WHERE " + DatabaseHelper._id + " = ?",
                bindingArgs);
        database.close();
    }

    public void decreaseItemQuantity(Item item) {
        if (getQuantity(item.get_id()) <= 1) {
            return;
        }

        open();
        String valueToIncrementBy = "-1";
        String[] bindingArgs = new String[]{ valueToIncrementBy, item.get_id() };

        database.execSQL("UPDATE " + DatabaseHelper.TABLE_NAME +
                        " SET " + DatabaseHelper.ORDER_QUANTITY + " = " + DatabaseHelper.ORDER_QUANTITY + " + ?" +
                        " WHERE " + DatabaseHelper._id + " = ?",
                bindingArgs);
        database.close();
    }

    public int getQuantity(String _id) {
        open();
        String query = "SELECT " + DatabaseHelper.ORDER_QUANTITY + " FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper._id + " = " + '"' + _id + '"';
        Cursor cursor = database.rawQuery( query, null );

        int orderQuantity = -1;
        if (cursor.moveToFirst()) {
            orderQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_QUANTITY));
        }

        cursor.close();
        database.close();
        return orderQuantity;
    }

    public boolean checkItemExistsInCart(String _id) {
        open();
        String[] columns = { DatabaseHelper._id };
        String selection = DatabaseHelper._id + " =?";
        String[] selectionArgs = { _id };
        String limit = "1";

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        database.close();
        return exists;
    }
}