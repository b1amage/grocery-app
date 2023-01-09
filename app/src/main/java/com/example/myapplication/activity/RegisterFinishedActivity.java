package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class RegisterFinishedActivity extends BaseActivity {

    private Button button_gosignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_finished);

        button_gosignin = (Button) findViewById(R.id.button_gosignin);
        button_gosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterFinishedActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}