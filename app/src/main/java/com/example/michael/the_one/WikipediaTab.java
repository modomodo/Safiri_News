package com.example.michael.the_one;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by Michael on 01/04/2015.
 */
public class WikipediaTab extends Fragment {

    SharedPreferences sharedPreferences;
    String query, query_check, url;
    private WebView webView;
    View v;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.wikipedia_tab, container, false);

        //Retrieve sharedPref value to use in parsing
        sharedPreferences = getActivity().getSharedPreferences("Safiri", getActivity().MODE_PRIVATE);
        query = sharedPreferences.getString("query", "Kenya");

        url = "http://en.m.wikipedia.org/wiki/" + query;
        query_check = query;

        final ProgressBar pBar = (ProgressBar) v.findViewById(R.id.progressBarWiki);
        pBar.setProgress(0);

        pBar.setVisibility(View.VISIBLE);

        webView = (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                pBar.setProgress(progress);
                if(progress == 100) {
                    pBar.setVisibility(View.GONE);
                    pBar.setProgress(0);
                }
            }
        });
        webView.loadUrl(url);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        query_check = sharedPreferences.getString("query", "Kenya");

        if(!query_check.equals(query)){
            url = "http://en.m.wikipedia.org/wiki/" + query_check;
            webView.reload();
            webView.loadUrl(url);
        }
    }

    // Open previous opened link from history on webview when back button pressed

}
