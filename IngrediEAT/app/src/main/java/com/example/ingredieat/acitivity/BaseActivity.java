package com.example.ingredieat.acitivity;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class BaseActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://10.0.2.2:8080";
    public static final String TAG = "Boston University";

    // GET request, no params
    public void getRequest(String requestUrl, Callback callback) {
        getRequest(requestUrl, null, callback);
    }

    // GET request, have params
    public void getRequest(final String requestUrl, final HashMap<String, String> params, Callback callback) {

        // build a okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        // concat each components of the url
        StringBuilder url = new StringBuilder(BASE_URL + requestUrl);
        if (params != null) {
            url.append("?");
            for (String key : params.keySet()) {
                url.append(key).append("=").append(params.get(key)).append("&");
            }
            url.deleteCharAt(url.length()-1);
        }

        Log.d(TAG, url.toString());

        // create the request content
        Request request = new Request.Builder()
                .get()
                .url(url.toString())
                .build();

        Call task = okHttpClient.newCall(request);
        task.enqueue(callback);
    }

    // POST request, no params
    public void postRequest(String requestUrl, Callback callback) {
        postRequest(requestUrl, null, callback);
    }

    // POST request, have params
    public void postRequest(String requestUrl, final HashMap<String, String> params, Callback callback) {

    }
}
