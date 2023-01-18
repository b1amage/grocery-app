package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class ErrorActivity extends AppCompatActivity {

    private TextView errText;
    private Button errBtn;

    private void initUIComponents() {
        errText = findViewById(R.id.err_text);
        errBtn = findViewById(R.id.err_btn);
    }

    private void setUpBtnBackHome() {
        errBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ErrorActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setErr() {
        String err = getIntent().getExtras().getString("error");
        errText.setText(err);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        initUIComponents();
        setUpBtnBackHome();
        setErr();
    }
}