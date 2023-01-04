package com.example.myapplication.api;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class APIHandler {
    private static final String url = "https://grocery-app-backend.vercel.app/api";
    private Context ctx;
    private SharedPreferences sharedPreferences;

    public APIHandler(Context ctx) {
        this.ctx = ctx;
        this.sharedPreferences = ctx.getSharedPreferences("Cookies", MODE_PRIVATE);
    }

    private void storeCookie(NetworkResponse response) {
        Map<String, String> responseHeaders = response.headers;
        if (responseHeaders == null) return;
        String rawCookies = responseHeaders.get("Set-Cookie");

        if (rawCookies != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("refreshToken", rawCookies);
            editor.apply();
        }
    }

    private Map<String, String> retrieveCookie(Map<String, String> headers) {
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }

        String refreshToken = sharedPreferences.getString("refreshToken", "");
        headers.put("Cookie", refreshToken);

        return headers;
    }

    private void removeCookie() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("refreshToken");
        editor.apply();
    }

    private boolean checkUnexpectedError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return true;
        } else if (error instanceof AuthFailureError) {
            return true;
        } else if (error instanceof ServerError) {
            return true;
        } else if (error instanceof NetworkError) {
            return true;
        } else return error instanceof ParseError;
    }

    public void postRequest(JSONObject jsonObject, String endpoint, VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + endpoint, jsonObject, response -> {
            Log.i("result", response.toString());
            try {
                listener.onResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            if (checkUnexpectedError(error)) {
                listener.onError(error.getMessage());
            }
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String parsed;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(networkResponse.data);
                }

                try {
                    JSONObject res = new JSONObject(parsed);
                    listener.onError(res.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                storeCookie(response);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                return retrieveCookie(headers);
            }
        };

        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//         Access the RequestQueue through your singleton class.
        APIRequest.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void getRequest(String endpoint, VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url + endpoint, null, response -> {
                    Log.i("result", response.toString());
                    try {
                        listener.onResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                    if (checkUnexpectedError(error)) {
                        listener.onError(error.getMessage());
                    }
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String parsed;
                        try {
                            parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                        } catch (UnsupportedEncodingException e) {
                            parsed = new String(networkResponse.data);
                        }

                        try {
                            JSONObject res = new JSONObject(parsed);
                            listener.onError(res.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                storeCookie(response);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                return retrieveCookie(headers);
            }
        };

        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Access the RequestQueue through your singleton class.
        APIRequest.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void deleteRequest(String endpoint, String id, VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                url + endpoint + "/" + id, null, response -> {
            Log.i("result", response.toString());
            try {
                listener.onResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            if (checkUnexpectedError(error)) {
                listener.onError(error.getMessage());
            }
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String parsed;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(networkResponse.data);
                }

                try {
                    JSONObject res = new JSONObject(parsed);
                    listener.onError(res.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                return retrieveCookie(headers);
            }
        };

        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Access the RequestQueue through your singleton class.
        APIRequest.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void updateRequest(JSONObject jsonObject, String endpoint, VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                url + endpoint, jsonObject, response -> {
            Log.i("result", response.toString());
            try {
                listener.onResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            if (checkUnexpectedError(error)) {
                listener.onError(error.getMessage());
            }
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String parsed;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(networkResponse.data);
                }

                try {
                    JSONObject res = new JSONObject(parsed);
                    listener.onError(res.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                storeCookie(response);
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                return retrieveCookie(headers);
            }
        };

        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Access the RequestQueue through your singleton class.
        APIRequest.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void logoutRequest(String endpoint, VolleyResponseListener listener) {
        Log.i("refreshToken", sharedPreferences.getString("refreshToken", ""));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                url + endpoint, null, response -> {
            Log.i("result", response.toString());
            try {
                listener.onResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            removeCookie();
        }, error -> {
            error.printStackTrace();
            if (checkUnexpectedError(error)) {
                listener.onError(error.getMessage());
            }
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {
                String parsed;
                try {
                    parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(networkResponse.data);
                }

                try {
                    JSONObject res = new JSONObject(parsed);
                    listener.onError(res.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                return retrieveCookie(headers);
            }
        };

        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Access the RequestQueue through your singleton class.
        APIRequest.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    private byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadBitmap(ImageView imageView, VolleyResponseListener listener) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url + "/image/upload-image",
                response -> {
                    if (response != null && response.data != null) {
                        String parsed;
                        try {
                            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        } catch (UnsupportedEncodingException e) {
                            parsed = new String(response.data);
                        }

                        try {
                            JSONObject res = new JSONObject(parsed);
                            listener.onResponse(res);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
                    error.printStackTrace();
                    if (checkUnexpectedError(error)) {
                        listener.onError(error.getMessage());
                    }
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String parsed;
                        try {
                            parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                        } catch (UnsupportedEncodingException e) {
                            parsed = new String(networkResponse.data);
                        }

                        try {
                            JSONObject res = new JSONObject(parsed);
                            listener.onError(res.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = super.getHeaders();
                        if (headers == null || headers.equals(Collections.emptyMap())) {
                            headers = new HashMap<>();
                        }

//                        headers.put("Content-Type", "multipart/form-data");
                        Log.i("headers", headers.toString());
                        return headers;
                    }

                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        long imagename = System.currentTimeMillis();
                        params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(ctx, imageView.getDrawable()), "image/jpg"));
                        return params;
                    }
        };

        //adding the request to volley
        APIRequest.getInstance(ctx).addToRequestQueue(volleyMultipartRequest);
    }
}
