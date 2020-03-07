package com.example.sse.customlistview_sse;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private String message;
    private String[] titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        this.webView = (WebView) findViewById(R.id.web);
        this.titles = getApplication().getResources().getStringArray(R.array.episodes);

        Bundle bundle = getIntent().getExtras();
        this.message = bundle.getString("message");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new myWebClient());
        showWeb();
    }


    public void showWeb() {
        if (message.equals("Spock's Brain"))
            webView.loadUrl("https://en.wikipedia.org/wiki/Spock%27s_Brain");
        else if (message.equals("Arena"))
            webView.loadUrl("https://en.wikipedia.org/wiki/Arena");
        else if (message.equals( "This Side of Paradise"))
            webView.loadUrl("https://en.wikipedia.org/wiki/This_Side_of_Paradise");
        else if (message.equals("Mirror, Mirror"))
            webView.loadUrl("https://en.wikipedia.org/wiki/Mirror_Mirror_(film)");
        else if (message.equals("Plato's Step Children"))
            webView.loadUrl("https://en.wikipedia.org/wiki/Plato%27s_Stepchildren");
        else if (message.equals("The Naked Time"))
            webView.loadUrl("https://en.wikipedia.org/wiki/The_Naked_Time");
        else if (message.equals("The Trouble With Tribbles"))
            webView.loadUrl("https://en.wikipedia.org/wiki/The_Trouble_with_Tribbles");
    }


    public class myWebClient extends WebViewClient
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        }

}
