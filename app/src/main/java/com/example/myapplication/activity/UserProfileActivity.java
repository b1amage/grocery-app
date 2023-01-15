package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.utilities.CookieManager;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ProgressBar progressBar = findViewById(R.id.waiting_for_user_profile);
        progressBar.setVisibility(View.VISIBLE);

        ImageButton backToMainActivity = findViewById(R.id.back_to_main_activity);
        backToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView userName = findViewById(R.id.user_name);
        TextView userEmail = findViewById(R.id.user_email);
        TextView userRole = findViewById(R.id.user_role);
        TextView userPhone = findViewById(R.id.user_phone);
        TextView userAddress = findViewById(R.id.user_address);
        TextView userPoints = findViewById(R.id.user_points);

        boolean isLogin = (new CookieManager(UserProfileActivity.this)).isLogin();
        if (!isLogin) {
            Intent intent = new Intent(UserProfileActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }

        String userId = (new CookieManager(UserProfileActivity.this)).getUserId();
        (new APIHandler(UserProfileActivity.this)).getRequest("/user/view/" + userId, new VolleyResponseListener() {
            @Override
            public void onError(String message, int statusCode) {
                System.out.println(message);
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                progressBar.setVisibility(View.GONE);

                JSONObject user = (JSONObject) response.get("user");
                userName.setText(user.get("username").toString());
                userEmail.setText(user.get("email").toString());
                userPhone.setText(user.get("phone").toString());
                userRole.setText(user.get("role").toString());
                userAddress.setText(user.get("address").toString());
                userPoints.setText(user.get("points").toString());
            }
        });
    }
}