package com.jeff.android_jeff_db;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    private String triggerWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //获取intent
        triggerWebsite = getIntent().getStringExtra("device_website");

        WebView webView = (WebView) findViewById(R.id.webview_video);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);

        //interface
        webView.setWebViewClient(new WebViewClient());//用户点击的所有链接都会在您的 WebView 中加载
        webView.addJavascriptInterface(new WebAppInterface(this, 1.0f, 1.0f,triggerWebsite), "Android");//绑定到Jscript，创建名为 Android 的接口
        webView.loadUrl("file:///android_asset/web/video.html");
    }
}
