package com.example.sse.customlistview_sse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        this.webView = (WebView) findViewById(R.id.web);
        Bundle bundle = getIntent().getExtras();
        this.message = bundle.getString("message");

        showWeb();

    }


    public void showWeb() {
        switch (message) {
            case "google":
                webView.loadUrl("https://www.google.com");
        }
    }

}
