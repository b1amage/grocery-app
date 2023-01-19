package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class VoucherForm extends BaseActivity {
    private ActionBar voucherFormActionBar = new ActionBar(R.id.voucherFormActionBar, this);
    private Button voucherFormButton = new Button(R.id.voucherSubmitButton, this);
    private EditText inputVoucherCodeText;
    private EditText inputVoucherTitleText;
    private EditText inputVoucherDescriptionText;
    private RadioButton percentageRadio;
    private RadioButton currencyRadio;
    private Voucher voucher;
    private EditText inputVoucherValueText;
    private String typeSelection;
    private TextView voucherCodeError;
    private TextView voucherTitleError;
    private TextView voucherDescriptionError;
    private TextView voucherTypeError;
    private TextView voucherValueError;
    private TextView overallError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_form);

        if (getIntent().getStringExtra("title") == null){
            voucherFormActionBar.createActionBar("Create voucher", R.drawable.ic_back, R.drawable.navbutton_shape);
            voucherFormButton.createActiveButton("Create", onSubmitFormClick());
        } else{
            voucherFormActionBar.createActionBar("Update voucher", R.drawable.ic_back, R.drawable.navbutton_shape);
            voucherFormButton.createActiveButton("Update", onSubmitFormClick());
        }

        inputVoucherCodeText = findViewById(R.id.inputVoucherCodeText);
        inputVoucherTitleText = findViewById(R.id.inputVoucherTitleText);
        inputVoucherDescriptionText = findViewById(R.id.inputVoucherDescriptionText);
        percentageRadio = findViewById(R.id.percentage);
        currencyRadio = findViewById(R.id.currency);
        inputVoucherValueText = findViewById(R.id.inputVoucherValueText);

        voucherCodeError = findViewById(R.id.inputVoucherCodeError);
        voucherTitleError = findViewById(R.id.inputVoucherTitleError);
        voucherDescriptionError = findViewById(R.id.inputVoucherDescriptionError);
        voucherTypeError = findViewById(R.id.inputVoucherTypeError);
        voucherValueError = findViewById(R.id.inputVoucherValueError);
        overallError = findViewById(R.id.overallError);

        inputVoucherCodeText.addTextChangedListener(getInputValue(inputVoucherCodeText));
        inputVoucherTitleText.addTextChangedListener(getInputValue(inputVoucherTitleText));
        inputVoucherDescriptionText.addTextChangedListener(getInputValue(inputVoucherDescriptionText));
        inputVoucherValueText.addTextChangedListener(getInputValue(inputVoucherValueText));

        voucher = (Voucher) getIntent().getSerializableExtra("voucher");

        if (voucher != null){
            inputVoucherCodeText.setText(voucher.getCode());
            inputVoucherTitleText.setText(voucher.getTitle());
            inputVoucherDescriptionText.setText(voucher.getDescription());
            if (voucher.getType().equalsIgnoreCase(percentageRadio.getText().toString())){
                percentageRadio.setChecked(true);
                typeSelection = percentageRadio.getText().toString();
            } else {
                currencyRadio.setChecked(true);
                typeSelection = currencyRadio.getText().toString();
            }
            inputVoucherValueText.setText(String.valueOf(voucher.getValue()));
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.percentage:
                if (checked)
                    typeSelection = percentageRadio.getText().toString();
                    break;
            case R.id.currency:
                if (checked)
                    typeSelection = currencyRadio.getText().toString();
                break;
        }
    }

    private View.OnClickListener onSubmitFormClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasError = false;

                voucherCodeError.setText("");
                voucherTitleError.setText("");
                voucherDescriptionError.setText("");
                voucherTypeError.setText("");
                voucherValueError.setText("");
                overallError.setText("");

                if (inputVoucherCodeText.getText().toString().isEmpty()){
                    voucherCodeError.setText("* Please provide a code");
                    hasError = true;
                }
                if (inputVoucherTitleText.getText().toString().isEmpty()) {
                    voucherTitleError.setText("* Please provide voucher title");
                    hasError = true;
                }
                if (inputVoucherDescriptionText.getText().toString().isEmpty()) {
                    voucherDescriptionError.setText("* Please provide a description");
                    hasError = true;
                }

                if (typeSelection == null) {
                    voucherTypeError.setText("* Please provide a voucher type");
                    hasError = true;
                }

                if (inputVoucherValueText.getText().toString().isEmpty() || !isInteger(inputVoucherValueText.getText().toString())){
                    voucherValueError.setText("* Please provide voucher value");
                    hasError = true;
                } else{
                    if (typeSelection.equalsIgnoreCase("percentage")){
                        if ((Integer.parseInt(String.valueOf(inputVoucherValueText.getText())) <= 0 || Integer.parseInt(String.valueOf(inputVoucherValueText.getText())) > 100)) {
                            voucherValueError.setText("* Value must be higher than 0 and lower 100");
                            hasError = true;
                        }
                    } else {
                        if (Integer.parseInt(String.valueOf(inputVoucherValueText.getText())) <= 0) {
                            voucherValueError.setText("* Value must be higher than 0");
                            hasError = true;
                        }
                    }
                }

                if (hasError){
                     return;
                }

                JSONObject postData = new JSONObject();
                try {
                    postData.put("code", inputVoucherCodeText.getText().toString().toUpperCase());
                    postData.put("title", inputVoucherTitleText.getText().toString());
                    postData.put("description", inputVoucherDescriptionText.getText().toString());
                    postData.put("type", typeSelection.toLowerCase());
                    postData.put("value", Integer.parseInt(String.valueOf(inputVoucherValueText.getText())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                (new APIHandler(VoucherForm.this)).postRequest(postData, "/voucher/create", new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        System.err.println(message);
                        overallError.setText("* " + message);
                    }

                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        System.out.println(response);
                        overallError.setText("Voucher created successful!");
                        overallError.setTextColor(getResources().getColor(R.color.primary_100));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), VoucherManagement.class));
                            }
                        }, 2000);
                    }

                });
            }
        };
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}