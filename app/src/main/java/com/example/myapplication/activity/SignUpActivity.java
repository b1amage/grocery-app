package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.utilities.Text;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends BaseActivity {

    private Button button_signup;
    private TextView text_signin;
    private EditText edittext_email, edittext_name, edittext_phone, edittext_address;
    private TextInputEditText edittext_password, edittext_confirmPassword;
    private TextInputLayout password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        button_signup = (Button) findViewById(R.id.button_signup);
        edittext_email = (EditText) findViewById(R.id.edittext_email);
        edittext_name = (EditText) findViewById(R.id.edittext_name);
        edittext_phone = (EditText) findViewById(R.id.edittext_phone);
        edittext_address = (EditText) findViewById(R.id.edittext_address);
        edittext_password = (TextInputEditText) findViewById(R.id.edittext_password);
        edittext_confirmPassword = (TextInputEditText) findViewById(R.id.edittext_confirmPassword);
        password = (TextInputLayout) findViewById(R.id.password);
        confirmPassword = (TextInputLayout) findViewById(R.id.confirmPassword);
        text_signin = (TextView) findViewById(R.id.text_signin);

        text_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cont = true;
                if(!edittext_password.getText().toString().equals(edittext_confirmPassword.getText().toString())){
                    edittext_confirmPassword.setError("Password does not match");
                    confirmPassword.setEndIconVisible(false);
                    cont = false;
                }
                if(TextUtils.isEmpty(edittext_address.getText())){
                    edittext_address.setError("Address can not be empty");
                    cont = false;
                }
                if(cont){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username",edittext_name.getText().toString());
                        jsonObject.put("password",edittext_password.getText().toString());
                        jsonObject.put("email",edittext_email.getText().toString());
                        jsonObject.put("phone",edittext_phone.getText().toString());
                        jsonObject.put("address",edittext_address.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    (new APIHandler(SignUpActivity.this)).postRequest(jsonObject, "/auth/register", new VolleyResponseListener() {
                        @Override
                        public void onError(String message, int statusCode) {
                            if(message.contains("email")){
                                edittext_email.setError(message);
                            } else if(message.contains("password")){
                                edittext_password.setError(message);
                                password.setEndIconVisible(false);
                            } else if(message.contains("username")){
                                edittext_name.setError(message);
                            } else if(message.contains("phone")){
                                edittext_phone.setError(message);
                            } else {
                                System.out.println(message + statusCode);
                            }
                        }
                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            jsonObject.put("hash", response.get("hash"));
                            Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
                            intent.putExtra("user",jsonObject.toString());
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }
}