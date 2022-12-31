package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.utilities.Input;

public class CreateItemForm extends AppCompatActivity {

    private ActionBar itemFormActionBar = new ActionBar(R.id.itemFormActionBar, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_form);

        itemFormActionBar.createActionBar("Create new item", R.drawable.ic_back, R.drawable.navbutton_shape);


//        inputItemName.createInput("Item image", 20, R.color.black, "Item name ...");

    }
}