package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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
import com.example.myapplication.utilities.ImageLoader;
import com.example.myapplication.utilities.Input;
import com.squareup.picasso.Picasso;

public class CreateItemForm extends BaseActivity {
    private ActionBar itemFormActionBar = new ActionBar(R.id.itemFormActionBar, this);
    private Button submitButton = new Button(R.id.submitButton, this);

    private Item newItem;

    private EditText inputItemNameText;
    private EditText inputItemCategoryText;
    private EditText inputItemPriceText;
    private EditText inputItemQuantityText;
    private EditText inputItemDescriptionText;
    private ImageButton uploadImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_form);

        itemFormActionBar.createActionBar("Create new item", R.drawable.ic_back, R.drawable.navbutton_shape);
        submitButton.createActiveButton("Create", onSubmitFormClick());
        uploadImageButton = findViewById(R.id.uploadImageButton);
        inputItemNameText = findViewById(R.id.inputItemText);
        inputItemCategoryText = findViewById(R.id.inputItemCategoryText);
        inputItemPriceText = findViewById(R.id.inputPriceText);
        inputItemDescriptionText = findViewById(R.id.inputItemDescriptionText);
        inputItemQuantityText = findViewById(R.id.inputItemQuantityText);

        inputItemNameText.addTextChangedListener(getInputValue(inputItemNameText));
        inputItemCategoryText.addTextChangedListener(getInputValue(inputItemCategoryText));
        inputItemPriceText.addTextChangedListener(getInputValue(inputItemPriceText));
        inputItemDescriptionText.addTextChangedListener(getInputValue(inputItemDescriptionText));
        inputItemQuantityText.addTextChangedListener(getInputValue(inputItemQuantityText));

        Item item = (Item) getIntent().getSerializableExtra("item");
        System.out.println(item);
        if (item != null){
            inputItemNameText.setText(item.getName());
            inputItemCategoryText.setText(item.getCategory());
            inputItemDescriptionText.setText(item.getDescription());
            inputItemPriceText.setText(String.valueOf(item.getPrice()));
            inputItemQuantityText.setText(String.valueOf(item.getQuantity()));
            ImageLoader.loadImg(item.getImageURL(), uploadImageButton);
        }

        uploadImageButton.setOnClickListener(uploadImageOnClick());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    uploadImageButton.setImageURI(selectedImageUri);
                    System.out.println(selectedImageUri);
                }
            }
        }
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
    private View.OnClickListener uploadImageOnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
            }
        };
    }
    private View.OnClickListener onSubmitFormClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                newItem = new Item(6, inputItemText.getText().toString(), R.drawable.dummy_item, inputShopText.getText().toString(), Double.parseDouble(inputPriceText.getText().toString()));
                Toast.makeText(CreateItemForm.this, newItem.toString(), Toast.LENGTH_LONG).show();
                finish();
            }
        };
    }
}