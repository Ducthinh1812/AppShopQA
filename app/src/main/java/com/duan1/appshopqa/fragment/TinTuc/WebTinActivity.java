package com.duan1.appshopqa.fragment.TinTuc;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.duan1.appshopqa.R;

public class WebTinActivity extends AppCompatActivity {
    WebView webView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_tin);
        webView=findViewById(R.id.west);

        intent=getIntent();
        String link=intent.getStringExtra("TinTucTT");
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}