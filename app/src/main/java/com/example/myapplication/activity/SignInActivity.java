package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class SignInActivity extends BaseActivity {

    private EditText editText_email,editText_password;
    private Button button_signIn;
    private TextView text_forgot, text_signUp;
    private CheckBox checkBox_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }
}