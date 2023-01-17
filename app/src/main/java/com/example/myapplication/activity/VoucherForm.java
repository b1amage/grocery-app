package com.example.myapplication.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;

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
    private Voucher newVoucher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_form);

        voucherFormActionBar.createActionBar("Create voucher", R.drawable.ic_back, R.drawable.navbutton_shape);
        voucherFormButton.createActiveButton("Create", onSubmitFormClick());

//        inputItemName.createInput("Item image", 20, R.color.black, "Item name ...");
        inputVoucherCodeText = findViewById(R.id.inputVoucherCodeText);
        inputVoucherTitleText = findViewById(R.id.inputVoucherTitleText);
        inputVoucherDescriptionText = findViewById(R.id.inputVoucherDescriptionText);
        percentageRadio = findViewById(R.id.percentage);
        currencyRadio = findViewById(R.id.currency);
        inputVoucherValueText = findViewById(R.id.inputVoucherValueText);

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
                newVoucher = new Voucher(voucher.get_id(), inputVoucherCodeText.getText().toString(), inputVoucherTitleText.getText().toString(), inputVoucherDescriptionText.getText().toString(), typeSelection,Integer.parseInt(String.valueOf(inputVoucherValueText.getText())));
                Toast.makeText(VoucherForm.this, newVoucher.toString(), Toast.LENGTH_LONG).show();
                finish();
            }
        };
    }
}