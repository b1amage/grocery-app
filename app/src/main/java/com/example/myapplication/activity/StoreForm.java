package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.Button;

public class StoreForm extends AppCompatActivity {

    private ActionBar storeFormActionBar = new ActionBar(R.id.storeFormActionBar, this);
    private Button storeFormButton = new Button(R.id.storeFormButton, this);
    private ImageButton uploadImageButton;

    private Item newItem;

    private EditText inputAddressText;
    private EditText inputLatitudeText;
    private EditText inputLongitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_form);

        storeFormActionBar.createActionBar("Create store location", R.drawable.ic_back, R.drawable.navbutton_shape);
        storeFormButton.createActiveButton("Create", onSubmitFormClick());

//        inputItemName.createInput("Item image", 20, R.color.black, "Item name ...");
        inputAddressText = findViewById(R.id.inputAddressText);
        inputLatitudeText = findViewById(R.id.inputLatitudeText);
        inputLongitudeText = findViewById(R.id.inputLongitudeText);

        inputAddressText.addTextChangedListener(getInputValue(inputAddressText));
        inputLatitudeText.addTextChangedListener(getInputValue(inputLatitudeText));
        inputLongitudeText.addTextChangedListener(getInputValue(inputLongitudeText));
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
//                newItem = new Item(6, inputItemText.getText().toString(), R.drawable.dummy_item, inputShopText.getText().toString(), Double.parseDouble(inputPriceText.getText().toString()));
                Toast.makeText(StoreForm.this, newItem.toString(), Toast.LENGTH_LONG).show();
            }
        };
    }
}