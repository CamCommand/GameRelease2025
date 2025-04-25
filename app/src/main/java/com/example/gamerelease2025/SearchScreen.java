package com.example.gamerelease2025;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SearchScreen extends AppCompatActivity {

    WebView webView = findViewById(R.id.webView);

    EditText urlBar;
    Button goButton;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        webView.getSettings().setJavaScriptEnabled(true);
        webView = findViewById(R.id.webView);
        urlBar = findViewById(R.id.urlBar);
        goButton = findViewById(R.id.goButton);

        // Enable JavaScript and stay inside the app
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlBar.getText().toString();
                if (!url.startsWith("http")) {
                    url = "https://google.com";
                }
                webView.loadUrl(url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Go back in WebView if possible
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}