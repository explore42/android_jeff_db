package com.jeff.android_jeff_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private String triggerName;
    private JeffDatabaseHelper dbHelper;

    private List<String> deviceListName = new ArrayList<String>();
    private List<String> deviceListBelong = new ArrayList<String>();
    private List<String> deviceListStatus = new ArrayList<String>();

    private List<DeviceDetail> deviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        triggerName = getIntent().getStringExtra("trigger_name");
        //Log.e("intent",triggerName);

        TextView textView = (TextView) findViewById(R.id.textview_detial_cabinet_name);
        textView.setText(triggerName);

        dbHelper = new JeffDatabaseHelper(this, "Device", null, 8);//每次需要更改版本号

        queryDatabaseDevice();
        initDevices();
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
}