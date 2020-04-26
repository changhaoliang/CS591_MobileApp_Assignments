package com.example.ingredieat.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils {
    public static final String BASE_URL = "http://3.17.56.44:8080";

    // GET request, no params
    public static void getRequest(String requestUrl, Callback callback)  {
        getRequest(requestUrl, null, callback);
    }

    // GET request, have params
    public static void getRequest(String requestUrl, Map<String, String> params, Callback callback)  {
        // create a okHttpClient
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
            url.deleteCharAt(url.length() - 1);
        }

        // create the request content
        Request request = new Request.Builder()
                .get()
                .url(url.toString())
                .build();

        Call task = okHttpClient.newCall(request);
        task.enqueue(callback);
    }

    // POST request, no params
    public static void postRequest(String requestUrl, Callback callback) {
        postRequest(requestUrl, "", callback);
    }

    // POST request, pass a map
    public static void postRequest(String requestUrl, Map<String, String> params, Callback callback) {
        String jsonString = JSON.toJSONString(params);
        System.out.println(jsonString);
        postRequest(requestUrl, jsonString, callback);
    }

    // POST request, pass a json String
    public static void postRequest(String requestUrl, String jsonString, Callback callback) {

        // create a okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build();

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonString, mediaType);

        String url = BASE_URL + requestUrl;

        // create the request content
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();

        Call task = okHttpClient.newCall(request);
        task.enqueue(callback);
    }
}
