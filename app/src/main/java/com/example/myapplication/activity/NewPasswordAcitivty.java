package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPasswordAcitivty extends BaseActivity {

    private ImageButton button_goback;
    private TextInputEditText edittext_password, edittext_confirmPassword;
    private TextInputLayout password, confirmPassword;
    private Button button_save;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password_acitivty);

        button_goback = (ImageButton) findViewById(R.id.button_goback);
        edittext_password = (TextInputEditText) findViewById(R.id.edittext_password);
        edittext_confirmPassword = (TextInputEditText) findViewById(R.id.edittext_confirmPassword);
        password = (TextInputLayout) findViewById(R.id.password);
        confirmPassword = (TextInputLayout) findViewById(R.id.confirmPassword);
        button_save = (Button) findViewById(R.id.button_save);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        edittext_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setEndIconVisible(true);
                edittext_password.setError(null);
            }
        });

        edittext_confirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPassword.setEndIconVisible(true);
                edittext_confirmPassword.setError(null);
            }
        });

        button_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewPasswordAcitivty.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cont = true;
                Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*].{8,}$");
                Matcher matcher = pattern.matcher(edittext_password.getText().toString());
                if(!matcher.find()){
                    edittext_password.setError("Password must have more than 8 character with at least 1 uppercase, 1 lowercase, 1 special character and 1 number");
                    password.setEndIconVisible(false);
                    cont = false;
                } else if(!edittext_password.getText().toString().equals(edittext_confirmPassword.getText().toString())){
                    edittext_confirmPassword.setError("Password does not match");
                    confirmPassword.setEndIconVisible(false);
                    cont = false;
                }
                if(cont){
                    try {
                        jsonObject.put("newPassword",edittext_password.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(NewPasswordAcitivty.this,OTPForgotActivity.class);
                    intent.putExtra("user",jsonObject.toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}