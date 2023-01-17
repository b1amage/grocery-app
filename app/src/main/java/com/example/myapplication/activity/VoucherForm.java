package com.example.myapplication.activity;

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
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;

public class VoucherForm extends BaseActivity {
    private ActionBar voucherFormActionBar = new ActionBar(R.id.voucherFormActionBar, this);
    private Button voucherFormButton = new Button(R.id.voucherSubmitButton, this);

    private EditText inputVoucherCodeText;
    private EditText inputVoucherTitleText;
    private EditText inputVoucherDescriptionText;
    private EditText inputVoucherTypeDiscountText;
    private EditText inputVoucherValueText;

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
        inputVoucherTypeDiscountText = findViewById(R.id.inputVoucherTypeDiscountText);
        inputVoucherValueText = findViewById(R.id.inputVoucherValueText);

        inputVoucherCodeText.addTextChangedListener(getInputValue(inputVoucherCodeText));
        inputVoucherTitleText.addTextChangedListener(getInputValue(inputVoucherTitleText));
        inputVoucherDescriptionText.addTextChangedListener(getInputValue(inputVoucherDescriptionText));
        inputVoucherTypeDiscountText.addTextChangedListener(getInputValue(inputVoucherTypeDiscountText));
        inputVoucherValueText.addTextChangedListener(getInputValue(inputVoucherValueText));

        Voucher voucher = (Voucher) getIntent().getSerializableExtra("voucher");
        System.out.println(voucher);
        if (voucher != null){
            inputVoucherCodeText.setText(voucher.getCode());
            inputVoucherTitleText.setText(voucher.getTitle());
            inputVoucherDescriptionText.setText(voucher.getDescription());
            inputVoucherTypeDiscountText.setText(String.valueOf(voucher.getType()));
            inputVoucherValueText.setText(String.valueOf(voucher.getValue()));
            newVoucher = voucher;
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
    private void uploadImageOnClick(){

    }
    private View.OnClickListener onSubmitFormClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                newItem = new Item(6, inputItemText.getText().toString(), R.drawable.dummy_item, inputShopText.getText().toString(), Double.parseDouble(inputPriceText.getText().toString()));
                Toast.makeText(VoucherForm.this, newVoucher.toString(), Toast.LENGTH_LONG).show();
                finish();
            }
        };
    }
}