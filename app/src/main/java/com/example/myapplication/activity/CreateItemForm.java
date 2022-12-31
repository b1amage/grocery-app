package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Preview;
import com.example.myapplication.R;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.Input;

public class CreateItemForm extends AppCompatActivity {

    private ActionBar itemFormActionBar = new ActionBar(R.id.itemFormActionBar, this);
    private Button submitButton = new Button(R.id.submitButton, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_form);

        itemFormActionBar.createActionBar("Create new item", R.drawable.ic_back, R.drawable.navbutton_shape);
        submitButton.createActiveButton("Create", activeButtonClick());

//        inputItemName.createInput("Item image", 20, R.color.black, "Item name ...");

    }

    private View.OnClickListener activeButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateItemForm.this, "Active button", Toast.LENGTH_LONG).show();
            }
        };
    }
}