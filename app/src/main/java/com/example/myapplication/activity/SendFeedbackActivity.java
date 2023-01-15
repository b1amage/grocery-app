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

public class SendFeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        ImageButton backToMainActivity = findViewById(R.id.back_to_main_activity);
        backToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendFeedbackActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        EditText inputTitle = findViewById(R.id.feedback_title);
        EditText inputDescription = findViewById(R.id.feedback_description);

        Button sendFeedbackBtn = findViewById(R.id.send_feedback_btn);
        sendFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = inputTitle.getText().toString();
                String description = inputDescription.getText().toString();

                JSONObject postData = new JSONObject();
                try {
                    postData.put("title", title);
                    postData.put("description", description);

                    (new APIHandler(SendFeedbackActivity.this)).postRequest(postData, "/feedback/create", new VolleyResponseListener() {
                        @Override
                        public void onError(String message, int statusCode) {
                            if (statusCode == 401) {
                                startActivity(new Intent(SendFeedbackActivity.this, SignInActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            startActivity(new Intent(SendFeedbackActivity.this, SendFeedbackFinishedActivity.class));
                            finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}