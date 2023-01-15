package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.example.myapplication.adapter.FeedbackAdapter;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Feedback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends BaseActivity {
    private ListView feedbackList;
    private ProgressBar progressBar;
    private List<Feedback> feedbacks;
    private FeedbackAdapter feedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        feedbackList = findViewById(R.id.feedback_list);
        progressBar = findViewById(R.id.waiting_for_feedbacks);
        ImageButton backToDashboardBtn = findViewById(R.id.back_to_dashboard);

        backToDashboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewFeedbackActivity.this, Dashboard.class));
                finish();
            }
        });

        (new APIHandler(ViewFeedbackActivity.this)).getRequest("/feedback/view", new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                if (statusCode == 403 || statusCode == 401) {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(ViewFeedbackActivity.this, SignInActivity.class));
                    finish();
                }
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                JSONArray jsonArray = response.getJSONArray("feedbacks");
                feedbacks = new ArrayList<>();

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String feedbackID = jsonObject.getString("_id");
                        String email = jsonObject.getString("email");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");

                        feedbacks.add(new Feedback(feedbackID, email, title, description));
                    }
                }

                feedbackAdapter = new FeedbackAdapter(feedbacks);
                feedbackList.setAdapter(feedbackAdapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}