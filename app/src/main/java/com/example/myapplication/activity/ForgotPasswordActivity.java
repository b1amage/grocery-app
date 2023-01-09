package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends BaseActivity {

    private Button button_next;
    private ImageButton button_goback;
    private EditText edittext_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        button_next = (Button) findViewById(R.id.button_next);
        button_goback = (ImageButton) findViewById(R.id.button_goback);
        edittext_email = (EditText) findViewById(R.id.edittext_email);

        button_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email",edittext_email.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                (new APIHandler(ForgotPasswordActivity.this)).postRequest(jsonObject, "/auth/forgot-password", new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        edittext_email.setError(message);
                    }
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        jsonObject.put("hash", response.get("hash"));
                        Intent intent = new Intent(ForgotPasswordActivity.this, NewPasswordAcitivty.class);
                        intent.putExtra("user",jsonObject.toString());
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}