package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Location;
import com.example.myapplication.utilities.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class StoreForm extends BaseActivity {

    private ActionBar storeFormActionBar = new ActionBar(R.id.storeFormActionBar, this);
    private Button storeFormButton = new Button(R.id.storeFormButton, this);

    private EditText inputAddressText;
    private EditText inputLatitudeText;
    private EditText inputLongitudeText;

    private TextView addressError;
    private TextView overallError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_form);

        if (getIntent().getStringExtra("title") == null){
            storeFormActionBar.createActionBar("Create store location", R.drawable.ic_back, R.drawable.navbutton_shape);
            storeFormButton.createActiveButton("Create", onSubmitFormClick());
        } else {
            storeFormActionBar.createActionBar("Update store location", R.drawable.ic_back, R.drawable.navbutton_shape);
            storeFormButton.createActiveButton("Update", onSubmitFormClick());
        }

        inputAddressText = findViewById(R.id.inputAddressText);
        inputLatitudeText = findViewById(R.id.inputLatitudeText);
        inputLongitudeText = findViewById(R.id.inputLongitudeText);
        addressError = findViewById(R.id.inputAddressError);
        overallError = findViewById(R.id.overallError);

        inputAddressText.addTextChangedListener(getInputValue(inputAddressText));
        inputLatitudeText.addTextChangedListener(getInputValue(inputLatitudeText));
        inputLongitudeText.addTextChangedListener(getInputValue(inputLongitudeText));

        Location location = (Location) getIntent().getSerializableExtra("location");
        System.out.println(location);
        if (location != null){
            inputAddressText.setText(location.getAddress());
            inputLatitudeText.setText(String.valueOf(location.getLatitude()));
            inputLongitudeText.setText(String.valueOf(location.getLongitude()));
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

    private View.OnClickListener onSubmitFormClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = inputAddressText.getText().toString();
                addressError.setText("");
                boolean hasError = false;
                Geocoder coder = new Geocoder(getApplicationContext());
                if (address.isEmpty()){
                    addressError.setText("* Please provide the address");
                    hasError = true;
                }

                if (hasError){
                    return;
                }

                try {
                    ArrayList<Address> addresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
                    if (addresses.size() > 0){
                        inputLatitudeText.setText(String.valueOf(addresses.get(0).getLatitude()));
                        inputLongitudeText.setText(String.valueOf(addresses.get(0).getLongitude()));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject postData = new JSONObject();
                                try {
                                    postData.put("address", address);
                                    postData.put("latitude", addresses.get(0).getLatitude());
                                    postData.put("longitude", addresses.get(0).getLongitude());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                (new APIHandler(StoreForm.this)).postRequest(postData, "/location/create", new VolleyResponseListener() {
                                    @Override
                                    public void onError(String message, int statusCode) {
                                        if (statusCode == 401 || statusCode == 403) {
                                            startActivity(new Intent(StoreForm.this, SignInActivity.class));
                                            finish();
                                            return;
                                        }
                                        System.err.println(message);
                                        overallError.setText("* " + message);
                                    }

                                    @Override
                                    public void onResponse(JSONObject response) throws JSONException {
                                        System.out.println(response);
                                        overallError.setText("Location created successful!");
                                        overallError.setTextColor(getResources().getColor(R.color.primary_100));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                startActivity(new Intent(StoreForm.this, LocationManagement.class));
                                            }
                                        }, 2000);
                                    }

                                });
                            }
                        }, 2000);
                    } else{
                        addressError.setText("* Location is not identify");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}