package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Preview;
import com.example.myapplication.R;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.Input;

public class CreateItemForm extends AppCompatActivity {

    private ActionBar itemFormActionBar = new ActionBar(R.id.itemFormActionBar, this);
    private Button submitButton = new Button(R.id.submitButton, this);
    private ImageButton uploadImageButton;

    private Item newItem;

    private EditText inputItemText;
    private EditText inputShopText;
    private EditText inputPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_form);

        itemFormActionBar.createActionBar("Create new item", R.drawable.ic_back, R.drawable.navbutton_shape);
        submitButton.createActiveButton("Create", onSubmitFormClick());
        uploadImageButton = findViewById(R.id.uploadImageButton);
//        inputItemName.createInput("Item image", 20, R.color.black, "Item name ...");
        inputItemText = findViewById(R.id.inputItemText);
        inputShopText = findViewById(R.id.inputShopText);
        inputPriceText = findViewById(R.id.inputPriceText);

        inputItemText.addTextChangedListener(getInputValue(inputItemText));
        inputShopText.addTextChangedListener(getInputValue(inputShopText));
        inputPriceText.addTextChangedListener(getInputValue(inputPriceText));


    }

    private TextWatcher getInputValue(EditText editText){
        GradientDrawable drawable = (GradientDrawable)editText.getBackground();
        drawable.mutate();
        drawable.setStroke(5, editText.getResources().getColor(R.color.tertiary_gray));

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int textLength = editText.getText().toString().length();
                drawable.setStroke(5, editText.getResources().getColor(textLength > 0 ? R.color.primary_100 : R.color.tertiary_gray)); // set stroke width and stroke color
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
    private void uploadImageOnClick(){

    }
    private View.OnClickListener onSubmitFormClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItem = new Item(6, inputItemText.getText().toString(), R.drawable.dummy_item, inputShopText.getText().toString(), Double.parseDouble(inputPriceText.getText().toString()));
                Toast.makeText(CreateItemForm.this, newItem.toString(), Toast.LENGTH_LONG).show();
            }
        };
    }
}