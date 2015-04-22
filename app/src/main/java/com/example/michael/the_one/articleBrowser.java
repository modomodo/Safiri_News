package com.example.michael.the_one;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class articleBrowser extends ActionBarActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_browser);
        getSupportActionBar().setTitle("Article Viewer");

        String url = getIntent().getStringExtra("articleUrl");

        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
