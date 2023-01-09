package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPActivity extends BaseActivity {

    private EditText code1, code2, code3, code4, code5, code6;
    private Button button_next;
    private TextView text_receive, text_resend, text_second;
    private JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
        code5 = findViewById(R.id.code5);
        code6 = findViewById(R.id.code6);
        button_next = (Button) findViewById(R.id.button_next);
        text_receive = (TextView) findViewById(R.id.text_receive);
        text_resend = (TextView) findViewById(R.id.text_resend);
        text_second = (TextView) findViewById(R.id.text_second);

        setUpOTP();
        reverseTimer();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = code1.getText().toString() +
                        code2.getText().toString() +
                        code3.getText().toString() +
                        code4.getText().toString() +
                        code5.getText().toString() +
                        code6.getText().toString();
                try {
                    jsonObject.put("otp",code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                (new APIHandler(OTPActivity.this)).postRequest(jsonObject, "/auth/verify-otp-register", new VolleyResponseListener() {
                    @Override
                    public void onError(String message, int statusCode) {
                        code6.setError(message);
                    }
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        Intent intent = new Intent(OTPActivity.this, RegisterFinishedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    public void reverseTimer(){
        new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                text_second.setText(millisUntilFinished / 1000 + " seconds" );
                // logic to set the EditText could go here
            }

            public void onFinish() {
                text_second.setText("Please resend the code");
                text_second.setTextColor(getResources().getColor(R.color.tertiary_gray));
                text_resend.setText("Resend here!");
                text_resend.setTextColor(getResources().getColor(R.color.primary_100));
                text_resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject temp = jsonObject;
                        try {
                            temp.getJSONObject("user").remove("hash");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        (new APIHandler(OTPActivity.this)).postRequest(temp, "/auth/register", new VolleyResponseListener() {
                            @Override
                            public void onError(String message, int statusCode) {
                            }
                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                jsonObject.put("hash", response.get("hash"));
                                reverseTimer();
                                text_resend.setText("Please wait!");
                                text_resend.setTextColor(getResources().getColor(R.color.tertiary_gray));
                                text_resend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                            }
                        });
                    }
                });
            }

        }.start();
    }


    private void setUpOTP(){
        code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    code2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    code3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    code4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    code5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    code6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}