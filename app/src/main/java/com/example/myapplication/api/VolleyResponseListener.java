package com.example.myapplication.api;

import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyResponseListener {
    void onError(String message, int statusCode);
    void onResponse(JSONObject response) throws JSONException;
}
