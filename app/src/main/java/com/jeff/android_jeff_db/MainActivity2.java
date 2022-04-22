package com.jeff.android_jeff_db;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        triggerLo = getIntent().getFloatExtra("trigger_lo", 103.931f);//后面的是默认值
        triggerLa = getIntent().getFloatExtra("trigger_la", 30.758f);
        //Log.e("intent",triggerName);

        TextView textView = (TextView) findViewById(R.id.textview_detial_cabinet_name);
        textView.setText(triggerName);

        dbHelper = new JeffDatabaseHelper(this, "Device", null, 12);//每次需要更改版本号

        queryDatabaseDevice();
        initDevices();
        initMaps();

        //sendRequestWithOKHttp();
    }

    private void queryDatabaseDevice() {
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
                if (belong.equals(triggerName)) {
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

    private void initDevices() {
        //实现对应设备的列表
        for (int i = 0; i < deviceListName.size(); i++) {
            deviceList.add(new DeviceDetail(deviceListName.get(i), deviceListBelong.get(i), deviceListStatus.get(i)));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_device_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DeviceDetailAdapter adapter = new DeviceDetailAdapter(deviceList);
        recyclerView.setAdapter(adapter);
    }

    private void initMaps() {
        locationMap = (WebView) findViewById(R.id.webview_maps);
        WebSettings webSettings = locationMap.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        locationMap.getSettings().setJavaScriptEnabled(true);

        //interface
        locationMap.setWebViewClient(new WebViewClient());//用户点击的所有链接都会在您的 WebView 中加载
        locationMap.addJavascriptInterface(new WebAppInterface(this, triggerLo, triggerLa), "Android");//绑定到Jscript，创建名为 Android 的接口

        locationMap.loadUrl("file:///android_asset/web/index.html");
    }

    //发起网络请求
    private void sendRequestWithOKHttp() {
        //先开启了一个子线程
        // 然后在子线程里使用OkHttp发出一条HTTP请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //Request request = new Request.Builder().url("https://www.baidu.com").build();//最终通过这个build方法展示出这个request，各种办法就包装在request中
                    Request request = new Request.Builder().url("http://10.0.2.2/get_data.xml").header("Connection", "close").build();//对于模拟器的本地地址
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //Log.d("https", responseData);

                    Log.e("jeff","start");
                    parseXMLWithPull(responseData);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseXMLWithPull(String xmlData) {
        //解析服务器返回的数据
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));//将得到的数据传进去
            int eventType = xmlPullParser.getEventType();//得到当前的解析事件

            String id = "";
            String name = "";
            String version = "";

            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();//得到当前结点的名字
                switch (eventType){
                    //开始解析某个结点
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id = xmlPullParser.nextText();//获得结点的具体内容
                        }
                        else if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }
                        else if("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }

                    //完成解析某个结点
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d("xml","id is "+id);
                            Log.d("xml","name is "+name);
                            Log.d("xml","version is "+version);
                        }
                        break;
                    }
                    default:break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}