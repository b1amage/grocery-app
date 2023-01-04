package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.utilities.Text;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private Button button_signup;
    private EditText edittext_email, edittext_name, edittext_phone, edittext_address;
    private TextInputEditText edittext_password, edittext_confirmPassword;

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



        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Toast.makeText(SignUpActivity.this, message + " " + statusCode, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        Toast.makeText(SignUpActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}