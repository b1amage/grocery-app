package com.example.myapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Preview;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.CustomSpinner;
import com.example.myapplication.utilities.ImageLoader;
import com.example.myapplication.utilities.Input;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateItemForm extends BaseActivity implements CustomSpinner.OnSpinnerEventsListener {
    private ActionBar itemFormActionBar = new ActionBar(R.id.itemFormActionBar, this);
    private Button submitButton = new Button(R.id.submitButton, this);

    private EditText inputItemNameText;
    private CustomSpinner categorySpinner;
    private EditText inputItemPriceText;
    private EditText inputItemQuantityText;
    private EditText inputItemDescriptionText;
    private ImageButton uploadImageButton;

    private TextView inputItemNameError;
    private TextView inputItemDescriptionError;
    private TextView inputItemPriceError;
    private TextView inputItemQuantityError;
    private TextView overallItemError;

    private CategoryAdapter categoryAdapter;
    private String imageURL = "";
    private String itemCategory = "";

    private String title = "";
    private String itemID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item_form);

        if (getIntent().getStringExtra("title") == null) {
            title = "create";
            itemFormActionBar.createActionBar("Create new item", R.drawable.ic_back, R.drawable.navbutton_shape);
            submitButton.createActiveButton("Create", onSubmitFormClick());
        } else {
            title = "update";
            itemFormActionBar.createActionBar(getIntent().getStringExtra("title"), R.drawable.ic_back, R.drawable.navbutton_shape);
            submitButton.createActiveButton("Update", onSubmitFormClick());
        }

        uploadImageButton = findViewById(R.id.uploadImageButton);
        inputItemNameText = findViewById(R.id.inputItemText);
        categorySpinner = findViewById(R.id.filter_spinner);
        inputItemPriceText = findViewById(R.id.inputPriceText);
        inputItemDescriptionText = findViewById(R.id.inputItemDescriptionText);
        inputItemQuantityText = findViewById(R.id.inputItemQuantityText);
        overallItemError = findViewById(R.id.overallItemError);

        inputItemNameText.addTextChangedListener(getInputValue(inputItemNameText));
        inputItemPriceText.addTextChangedListener(getInputValue(inputItemPriceText));
        inputItemDescriptionText.addTextChangedListener(getInputValue(inputItemDescriptionText));
        inputItemQuantityText.addTextChangedListener(getInputValue(inputItemQuantityText));

        inputItemNameError = findViewById(R.id.inputItemNameError);
        inputItemDescriptionError = findViewById(R.id.inputItemDescriptionError);
        inputItemPriceError = findViewById(R.id.inputItemPriceError);
        inputItemQuantityError = findViewById(R.id.inputItemQuantityError);

        Item item = (Item) getIntent().getSerializableExtra("item");
        System.out.println(item);
        if (item != null){
            itemID = item.get_id();
            inputItemNameText.setText(item.getName());
            System.out.println("des" + item.getDescription());
            itemCategory = item.getCategory();
            inputItemDescriptionText.setText(item.getDescription());
            inputItemPriceText.setText(String.valueOf(item.getPrice()));
            inputItemQuantityText.setText(String.valueOf(item.getQuantity()));
            imageURL = item.getImageURL();
            ImageLoader.loadImg(item.getImageURL(), uploadImageButton);
        }

        setUpSpinner();
        uploadImageButton.setOnClickListener(uploadImageOnClick());
    }

    private int getIndexSpinner(Adapter categoryAdapter, String value) {
        for (int index = 0, count = categoryAdapter.getCount(); index < count; ++index) {
            if (((Category) categoryAdapter.getItem(index)).getCategoryName().equals(value)) {
                return index;
            }
        }

        return -1;
    }

    private void setUpSpinner(){
        System.err.println("title " + title);
        System.err.println("itemCateg: " + itemCategory);
        categorySpinner.setSpinnerEventsListener(this);
        categoryAdapter = new CategoryAdapter(this, true);
        categorySpinner.setAdapter(categoryAdapter);

        if (title.equals("update")) {
            categorySpinner.setSelection(getIndexSpinner(categoryAdapter, itemCategory));
        }
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemCategory = ((Category) categorySpinner.getSelectedItem()).getCategoryName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            String filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    uploadImageButton.setImageBitmap(bitmap);
                    (new APIHandler(CreateItemForm.this)).uploadBitmap(uploadImageButton, new VolleyResponseListener() {
                        @Override
                        public void onError(String message, int statusCode) {
                            System.err.println(message);
                        }

                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            imageURL = response.getJSONObject("image").getString("src");
                            System.out.println("image url: " + imageURL);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(
                        CreateItemForm.this,"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
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
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(CreateItemForm.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(CreateItemForm.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(CreateItemForm.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                100);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);                }
            }
        };
    }
    private View.OnClickListener onSubmitFormClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputItemNameError.setText(null);
                inputItemPriceError.setText(null);
                inputItemQuantityError.setText(null);
                inputItemDescriptionError.setText(null);
                overallItemError.setText(null);

                boolean hasError = false;
                String itemName = inputItemNameText.getText().toString();
                String priceStr = inputItemPriceText.getText().toString();
                int price = 0;
                String quantityStr = inputItemQuantityText.getText().toString();
                int quantity = 0;
                String description = inputItemDescriptionText.getText().toString();

                if (imageURL.isEmpty()) {
                    overallItemError.setText("* Please upload image");
                    hasError = true;
                }

                if (itemCategory.isEmpty()) {
                    overallItemError.setText("* Please provide category");
                    hasError = true;
                }

                if (itemName.isEmpty()) {
                    inputItemNameError.setText("* Please provide name");
                    hasError = true;
                }

                if (priceStr.isEmpty()) {
                    inputItemPriceError.setText("* Please provide price");
                    hasError = true;
                } else {
                    try {
                        price = Integer.parseInt(priceStr);
                        if (price <= 0) {
                            inputItemPriceError.setText("* Price must be greater than 0");
                            hasError = true;
                        }
                    } catch (NumberFormatException e) {
                        inputItemPriceError.setText("* Please provide valid integer");
                        hasError = true;
                    }
                }

                if (quantityStr.isEmpty()) {
                    inputItemQuantityError.setText("* Please provide quantity");
                    hasError = true;
                } else {
                    try {
                        quantity = Integer.parseInt(quantityStr);
                        if (quantity <= 0) {
                            inputItemQuantityError.setText("* Quantity must be greater than 0");
                            hasError = true;
                        }
                    } catch (NumberFormatException e) {
                        inputItemQuantityError.setText("* Please provide valid quantity");
                        hasError = true;
                    }
                }

                if (description.isEmpty()) {
                    inputItemDescriptionError.setText("* Please provide description");
                    hasError = true;
                }

                if (hasError) return;

                JSONObject postData = new JSONObject();
                try {
                    postData.put("image", imageURL);
                    postData.put("name", itemName);
                    postData.put("description", description);
                    postData.put("price", price);
                    postData.put("category", itemCategory);
                    postData.put("quantity", quantity);

                    if (title.equals("create")) {
                        (new APIHandler(CreateItemForm.this)).postRequest(postData, "/item/create", new VolleyResponseListener() {
                            @Override
                            public void onError(String message, int statusCode) {
                                overallItemError.setText("* " + message);
                            }

                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                overallItemError.setText("Item created successful!");
                                overallItemError.setTextColor(getResources().getColor(R.color.primary_100));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 2000);
                            }
                        });
                    } else if (title.equals("update")) {
                        (new APIHandler(CreateItemForm.this)).updateRequest(postData, "/item/update/" + itemID, new VolleyResponseListener() {
                            @Override
                            public void onError(String message, int statusCode) {
                                overallItemError.setText("* " + message);
                            }

                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                overallItemError.setText("Item updated successful!");
                                overallItemError.setTextColor(getResources().getColor(R.color.primary_100));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 2000);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {

    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {

    }
}