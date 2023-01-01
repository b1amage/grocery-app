package com.example.myapplication.utilities;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class Input {
    protected int inputId;
    protected Activity activity;
    protected LinearLayout inputLayout;

    private Text label;
    private EditText inputText;

    private GradientDrawable drawable;

    public Input(int inputId, Activity _activity) {
        this.inputId = inputId;
        this.activity = _activity;
    }

    private void getInputElement(){
        inputLayout = activity.findViewById(inputId);
        label = new Text(R.id.label, activity);
        inputText = inputLayout.findViewById(R.id.inputText);
        drawable = (GradientDrawable)inputText.getBackground();
        drawable.mutate(); // only change this instance of the xml, not all components using this xml
    }

    // custom input
    public void createInput(String title, int size, int labelColor,String placeholder){
        getInputElement();
        label.createTitle(size,title, labelColor, Gravity.START);
        inputText.setHint(placeholder);
        drawable.setStroke(5, inputText.getResources().getColor(R.color.tertiary_gray)); // set stroke width and stroke color

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int textLength = inputText.getText().toString().length();
                drawable.setStroke(5, inputText.getResources().getColor(textLength > 0 ? R.color.primary_100 : R.color.tertiary_gray)); // set stroke width and stroke color
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

