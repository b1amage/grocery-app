package com.example.myapplication.components;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;

public class FilterCategory{
    private String[] categories;
    private Activity activity;
    private int listItemId;

    public FilterCategory(String[] categories, Activity activity, int listItemId) {
        this.categories = categories;
        this.activity = activity;
        this.listItemId = listItemId;
    }

    public void selectCategory(){
        AutoCompleteTextView autoCompleteTextView = activity.findViewById(R.id.autoCompleteTxt);
        ArrayAdapter<String> adapterCategories = new ArrayAdapter<>(activity.getApplicationContext(), listItemId, categories);
        autoCompleteTextView.setAdapter(adapterCategories);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(activity.getApplicationContext(), value, Toast.LENGTH_LONG).show();
            }
        });
    }
}
