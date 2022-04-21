package com.jeff.android_jeff_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private String triggerName;
    private Float triggerLo;
    private Float triggerLa;

    private JeffDatabaseHelper dbHelper;

    private List<String> deviceListName = new ArrayList<String>();
    private List<String> deviceListBelong = new ArrayList<String>();
    private List<String> deviceListStatus = new ArrayList<String>();

    private List<DeviceDetail> deviceList = new ArrayList<>();

    WebView locationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        triggerName = getIntent().getStringExtra("trigger_name");
        triggerLo = getIntent().getFloatExtra("trigger_lo",1.000f);
        triggerLa = getIntent().getFloatExtra("trigger_la",1.000f);
        //Log.e("intent",triggerName);

        TextView textView = (TextView) findViewById(R.id.textview_detial_cabinet_name);
        textView.setText(triggerName);

        dbHelper = new JeffDatabaseHelper(this, "Device", null, 12);//每次需要更改版本号

        queryDatabaseDevice();
        initDevices();
        initMaps();
    }

    private void queryDatabaseDevice(){
        //根据机柜名查询对应的设备，存放在列表中

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //all data
        Cursor cursor = db.query("Device", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //遍历全部对象
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String belong = cursor.getString(cursor.getColumnIndex("belong"));
                @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex("status"));
                //装入集合之中
                if(belong.equals(triggerName)){
                    deviceListName.add(name);
                    deviceListBelong.add(belong);
                    deviceListStatus.add(Integer.toString(status));
                    Log.d("DATABASE", "onClick: device name " + name);
                    Log.d("DATABASE", "onClick: device belong " + belong);
                    Log.d("DATABASE", "onClick: device status " + status);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void initDevices(){
        //实现对应设备的列表
        for (int i = 0; i < deviceListName.size(); i++) {
            deviceList.add(new DeviceDetail(deviceListName.get(i),deviceListBelong.get(i),deviceListStatus.get(i)));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_device_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DeviceDetailAdapter adapter = new DeviceDetailAdapter(deviceList);
        recyclerView.setAdapter(adapter);
    }

    private void initMaps(){
        locationMap = (WebView) findViewById(R.id.webview_maps);
        WebSettings webSettings = locationMap.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        locationMap.getSettings().setJavaScriptEnabled(true);

        //interface
        locationMap.setWebViewClient(new WebViewClient());//用户点击的所有链接都会在您的 WebView 中加载
        locationMap.addJavascriptInterface(new WebAppInterface(this,triggerLo,triggerLa), "Android");//绑定到Jscript，创建名为 Android 的接口

        locationMap.loadUrl("file:///android_asset/web/index.html");
    }
}