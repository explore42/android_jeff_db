package com.jeff.android_jeff_db;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private String triggerName;
    private Float triggerLo;
    private Float triggerLa;

    private List<String> deviceListName = new ArrayList<String>();
    private List<String> deviceListBelong = new ArrayList<String>();
    private List<String> deviceListStatus = new ArrayList<String>();

    private List<DeviceDetail> deviceList = new ArrayList<>();

    WebView locationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //从Intent中获得
        triggerName = getIntent().getStringExtra("trigger_name");
        triggerLo = getIntent().getFloatExtra("trigger_lo", 103.931f);//后面的是默认值
        triggerLa = getIntent().getFloatExtra("trigger_la", 30.758f);
        //Log.e("intent",triggerName);
        TextView textView = (TextView) findViewById(R.id.textview_detial_cabinet_name);//对应的机柜名
        textView.setText(triggerName);

        //0426 从新的Pal数据库中查询数据
        initListFromServer();
        initMaps();
    }

    private void initListFromServer() {//实现对应设备的列表
        //先查询数据库
        List<DevicePal> devicePals = LitePal.findAll(DevicePal.class);
        for (DevicePal devicePal : devicePals) {
            if (devicePal.getBelong().equals(triggerName)) {
                //注意顺序
                deviceList.add(new DeviceDetail(devicePal.getName(), devicePal.getBelong(), devicePal.getStatus(), devicePal.getLongitude(), devicePal.getLatitude(), devicePal.getInfo(), devicePal.getWebsite()));
            }
        }

        //再传入数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_device_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DeviceDetailAdapter adapter = new DeviceDetailAdapter(deviceList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//分割线
        recyclerView.setAdapter(adapter);
    }

    private void initMaps() {//下方显示地图
        locationMap = (WebView) findViewById(R.id.webview_maps);
        WebSettings webSettings = locationMap.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        locationMap.getSettings().setJavaScriptEnabled(true);

        //interface
        locationMap.setWebViewClient(new WebViewClient());//用户点击的所有链接都会在您的 WebView 中加载
        locationMap.addJavascriptInterface(new WebAppInterface(this, triggerLo, triggerLa,""), "Android");//绑定到Jscript，创建名为 Android 的接口

        locationMap.loadUrl("file:///android_asset/web/index.html");
    }
}