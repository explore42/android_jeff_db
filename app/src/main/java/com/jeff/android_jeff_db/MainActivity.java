package com.jeff.android_jeff_db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //0419 用这个实现数据的创建，加载数据到网页上
    //https://developer.android.com/training/data-storage/sqlite?hl=zh-cn

    //list的各种实现
    //https://weread.qq.com/web/reader/7c532360718ff6317c5255dkc1632f5021fc16a5320f3dc

    private JeffDatabaseHelper dbHelper;

    private String[] data = {"apple", "banana", "orange", "Jeff"};
    private List<Fruit> fruitList = new ArrayList<>();
    private List<Device> deviceList = new ArrayList<>();

    //存放从数据库读出来的数据
    //集合嵌套,https://blog.csdn.net/qq_36098284/article/details/79936335
    private List<String> deviceListName = new ArrayList<String>();
    private List<String> deviceListLo = new ArrayList<String>();
    private List<String> deviceListLa = new ArrayList<String>();
    private List<String> deviceListStatus = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new JeffDatabaseHelper(this, "Device", null, 2);//每次需要更改版本号
        //createDatabase();
        //addDatabase();
        //queryDatabase();

        //显示这个list
        //initFruits();
        initDevices();

        //Stetho.initializeWithDefaults(this);//用于显示数据库，no use
    }

    private void createDatabase() {
        Button createDBButton = (Button) findViewById(R.id.create_database);
        createDBButton.setVisibility(View.VISIBLE);
        createDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });
    }

    private void addDatabase() {
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setVisibility(View.VISIBLE);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //开始组装数据
                values.put("name", "device_1");
                values.put("longitude", "104.067");
                values.put("latitude", "30.658");
                values.put("status", "1");
                db.insert("Device", null, values);
                values.clear();
            }
        });
    }

    private void queryDatabase() {
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setVisibility(View.VISIBLE);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //all data
                Cursor cursor = db.query("Device", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        //遍历全部对象
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") float longitude = cursor.getFloat(cursor.getColumnIndex("longitude"));
                        @SuppressLint("Range") float latitude = cursor.getFloat(cursor.getColumnIndex("latitude"));
                        @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex("status"));
                        Log.d("DATABASE", "onClick: device name " + name);
                        Log.d("DATABASE", "onClick: device longitude " + longitude);
                        Log.d("DATABASE", "onClick: device latitude " + latitude);
                        Log.d("DATABASE", "onClick: device status " + status);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }

    private void initFruits() {
        for (int i = 0; i < 3; i++) {//让数据充满屏幕
            Fruit apple = new Fruit("Apple", R.drawable.apple);
            fruitList.add(apple);
        }

        //ListView listview = (ListView) findViewById(R.id.list_view);
        //listview.setAdapter(adapter);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FruitAdapter2 adapter = new FruitAdapter2(fruitList);
        recyclerView.setAdapter(adapter);

    }

    private void initDevices() {
        //for (int i = 0; i < 5; i++) {//让数据充满屏幕
        //    Device device1 = new Device("device_1", "100.123", "456.789", "1");
        //    deviceList.add(device1);
        //}

        queryDatabaseToList();
        for(int i = 0; i < deviceListName.size(); i++){
            deviceList.add(new Device(deviceListName.get(i),deviceListLo.get(i),deviceListLa.get(i),deviceListStatus.get(i)));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DeviceAdapter adapter = new DeviceAdapter(deviceList);
        recyclerView.setAdapter(adapter);
    }

    private void queryDatabaseToList() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //all data
        Cursor cursor = db.query("Device", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //遍历全部对象
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") float longitude = cursor.getFloat(cursor.getColumnIndex("longitude"));
                @SuppressLint("Range") float latitude = cursor.getFloat(cursor.getColumnIndex("latitude"));
                @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex("status"));
                Log.d("DATABASE", "onClick: device name " + name);
                Log.d("DATABASE", "onClick: device longitude " + longitude);
                Log.d("DATABASE", "onClick: device latitude " + latitude);
                Log.d("DATABASE", "onClick: device status " + status);

                //装入集合之中
                deviceListName.add(name);
                deviceListLo.add(Float.toString(longitude));
                deviceListLa.add(Float.toString(latitude));
                deviceListStatus.add(Integer.toString(status));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }
}