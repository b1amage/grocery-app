package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.google.android.gms.maps.model.Dash;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends BaseActivity {

    private EditText editText_email;
    private TextInputEditText editText_password;
    private Button button_signIn;
    private TextView text_forgot, text_signUp;
    private CheckBox checkBox_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editText_email = (EditText) findViewById(R.id.edittext_email);
        editText_password = (TextInputEditText) findViewById(R.id.edittext_password);
        button_signIn = (Button) findViewById(R.id.button_signin);
        text_forgot = (TextView) findViewById(R.id.text_forgot);
        text_signUp = (TextView) findViewById(R.id.text_signup);

        TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
        editText_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setEndIconVisible(true);
            }
        });

        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email",editText_email.getText().toString());
                    jsonObject.put("password",editText_password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                (new APIHandler(SignInActivity.this)).postRequest(jsonObject, "/auth/login", new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        if(message.contains("email")){
                            editText_email.setError(message);
                        } else if(message.contains("password")){
                            editText_password.setError(message);
                            password.setEndIconVisible(false);
                        } else {
                            System.out.println(message + statusCode);
                        }
                    }
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        JSONObject user = (JSONObject) response.get("user");

                        if(user.get("role").toString().equals("staff")){
                            Intent intent = new Intent(SignInActivity.this, Dashboard.class);
                            startActivity(intent);
                        } else if(user.get("role").toString().equals("customer")){
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        SharedPreferences sharedpreferences = getSharedPreferences("Cookies", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("role",user.get("role").toString());
                        editor.putString("userId",user.get("userId").toString());
                        editor.commit();
                        finish();
                    }
                });

            }
        });

        text_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        text_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}