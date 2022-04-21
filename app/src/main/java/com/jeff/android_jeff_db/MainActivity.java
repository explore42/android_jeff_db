package com.jeff.android_jeff_db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private List<String> deviceListName = new ArrayList<String>();
    private List<String> deviceListLo = new ArrayList<String>();
    private List<String> deviceListLa = new ArrayList<String>();
    private List<String> deviceListStatus = new ArrayList<String>();

    //原来的变量
    private String[] outName = {};
    private String[] outLo = {};
    private String[] outLa = {};
    private String[] outStatus = {};

    private String[] outCabinetName = {"机柜1", "机柜2", "机柜3", "机柜4", "机柜5", "机柜6", "机柜7", "机柜8", "机柜9", "机柜10", "机柜11", "机柜12", "机柜13", "机柜14", "机柜15",
            "机柜16", "机柜17", "机柜18", "机柜19", "机柜20", "机柜21", "机柜22", "机柜23", "机柜24", "机柜25", "机柜26", "机柜27", "机柜28", "机柜29", "机柜30", "机柜31", "机柜32",
            "机柜33", "机柜34", "机柜35", "机柜36", "机柜37", "机柜38", "机柜39", "机柜40", "机柜41", "机柜42", "机柜43", "机柜44", "机柜45", "机柜46", "机柜47", "机柜48", "机柜49", "机柜50"};
    private String[] outCabinetLo = {"104.021", "103.967", "104.017", "104.108", "103.965", "104.064", "104.115", "104.108", "103.936", "104.073", "103.966", "103.975", "104.001",
            "104.060", "103.975", "103.983", "103.965", "103.977", "104.069", "103.959", "104.021", "103.949", "104.135", "103.997", "104.135", "103.944", "103.947", "104.058", "104.103",
            "103.955", "104.064", "103.965", "104.025", "103.936", "103.984", "103.950", "103.941", "104.101", "104.129", "103.983", "104.061", "103.937", "103.996", "104.072", "104.104",
            "103.999", "104.092", "104.104", "104.114", "104.079"};
    private String[] outCabinetLa = {"30.637", "30.649", "30.669", "30.590", "30.670", "30.609", "30.577", "30.620", "30.633", "30.628", "30.575", "30.731", "30.742", "30.676", "30.627", "30.655",
            "30.612", "30.639", "30.730", "30.658", "30.637", "30.634", "30.658", "30.684", "30.672", "30.675", "30.601", "30.617", "30.666", "30.659", "30.707", "30.695", "30.701", "30.635",
            "30.593", "30.599", "30.660", "30.682", "30.679", "30.636", "30.582", "30.593", "30.586", "30.599", "30.653", "30.712", "30.682", "30.671", "30.700", "30.602"};
    private String[] outCabinetStatus = {"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0", "0", "0", "0"};

    private String[] outDeviceName = {"音箱1", "音箱2", "摄像头1", "摄像头2", "音箱3", "音箱4", "摄像头3", "音箱5", "音箱6", "音箱7", "音箱8", "摄像头4", "摄像头5", "摄像头6", "音箱9", "音箱10", "摄像头7", "摄像头8",
            "音箱11", "音箱12", "摄像头9", "摄像头10", "摄像头11", "音箱13", "音箱14", "音箱15", "音箱16", "摄像头12", "摄像头13", "摄像头14", "摄像头15", "音箱17", "音箱18", "音箱19", "音箱20", "摄像头16", "摄像头17",
            "摄像头18", "音箱21", "音箱22", "摄像头19", "音箱23", "音箱24", "摄像头20", "音箱25", "音箱26", "摄像头21", "摄像头22", "摄像头23", "摄像头24", "音箱27", "音箱28", "摄像头25", "摄像头26", "摄像头27", "摄像头28",
            "音箱29", "音箱30", "摄像头29", "摄像头30", "摄像头31", "音箱31", "音箱32", "摄像头32", "摄像头33", "摄像头34", "摄像头35", "音箱33", "音箱34", "摄像头36", "摄像头37", "摄像头38", "摄像头39", "音箱35", "音箱36",
            "摄像头40", "摄像头41", "摄像头42", "摄像头43", "音箱37", "音箱38", "摄像头44", "摄像头45", "摄像头46", "音箱39", "音箱40", "音箱41", "音箱42", "摄像头47", "摄像头48", "音箱43", "音箱44", "摄像头49", "音箱45",
            "音箱46", "摄像头50", "摄像头51", "音箱47", "音箱48", "音箱49", "音箱50", "摄像头52", "摄像头53", "摄像头54", "音箱51", "音箱52", "音箱53", "音箱54", "摄像头55", "摄像头56", "音箱55", "音箱56", "摄像头57", "音箱57",
            "音箱58", "摄像头58", "摄像头59", "摄像头60", "摄像头61", "音箱59", "音箱60", "摄像头62", "音箱61", "音箱62", "摄像头63", "音箱63", "音箱64", "摄像头64", "摄像头65", "摄像头66", "音箱65", "音箱66", "摄像头67",
            "摄像头68", "摄像头69", "音箱67", "音箱68", "摄像头70", "音箱69", "音箱70", "音箱71", "音箱72", "摄像头71", "音箱73", "音箱74", "摄像头72", "摄像头73", "摄像头74", "音箱75", "音箱76", "摄像头75", "摄像头76", "音箱77",
            "音箱78", "摄像头77", "摄像头78", "摄像头79", "音箱79", "音箱80", "摄像头80", "摄像头81", "摄像头82", "音箱81", "音箱82", "摄像头83", "摄像头84", "摄像头85", "摄像头86", "音箱83", "音箱84", "摄像头87", "音箱85", "音箱86",
            "摄像头88", "摄像头89", "摄像头90", "摄像头91", "音箱87", "音箱88", "摄像头92", "音箱89", "音箱90", "摄像头93", "摄像头94", "摄像头95", "音箱91", "音箱92", "摄像头96", "摄像头97", "摄像头98", "摄像头99", "音箱93", "音箱94",
            "摄像头100", "摄像头101", "摄像头102", "音箱95", "音箱96", "摄像头103", "音箱97", "音箱98", "摄像头104", "摄像头105", "摄像头106", "音箱99", "音箱100"};
    private String[] outDeviceBelong = {"机柜1", "机柜1", "机柜2", "机柜2", "机柜2", "机柜2", "机柜3", "机柜3", "机柜3", "机柜4", "机柜4", "机柜5", "机柜5", "机柜5", "机柜5", "机柜5", "机柜6", "机柜6", "机柜6", "机柜6", "机柜7",
            "机柜7", "机柜7", "机柜7", "机柜7", "机柜8", "机柜8", "机柜9", "机柜9", "机柜9", "机柜9", "机柜9", "机柜9", "机柜10", "机柜10", "机柜11", "机柜11", "机柜11", "机柜11", "机柜11", "机柜12", "机柜12", "机柜12", "机柜13",
            "机柜13", "机柜13", "机柜14", "机柜14", "机柜14", "机柜14", "机柜14", "机柜14", "机柜15", "机柜15", "机柜15", "机柜15", "机柜15", "机柜15", "机柜16", "机柜16", "机柜16", "机柜16", "机柜16", "机柜17", "机柜17", "机柜17",
            "机柜17", "机柜17", "机柜17", "机柜18", "机柜18", "机柜18", "机柜18", "机柜18", "机柜18", "机柜19", "机柜19", "机柜19", "机柜19", "机柜19", "机柜19", "机柜20", "机柜20", "机柜20", "机柜20", "机柜20", "机柜21", "机柜21",
            "机柜22", "机柜22", "机柜22", "机柜22", "机柜23", "机柜23", "机柜23", "机柜24", "机柜24", "机柜24", "机柜24", "机柜25", "机柜25", "机柜26", "机柜26", "机柜26", "机柜26", "机柜26", "机柜27", "机柜27", "机柜28", "机柜28",
            "机柜28", "机柜28", "机柜29", "机柜29", "机柜29", "机柜30", "机柜30", "机柜30", "机柜30", "机柜30", "机柜30", "机柜31", "机柜31", "机柜31", "机柜32", "机柜32", "机柜32", "机柜33", "机柜33", "机柜33", "机柜33", "机柜33",
            "机柜34", "机柜34", "机柜34", "机柜34", "机柜34", "机柜35", "机柜35", "机柜35", "机柜36", "机柜36", "机柜37", "机柜37", "机柜37", "机柜38", "机柜38", "机柜38", "机柜38", "机柜38", "机柜39", "机柜39", "机柜39", "机柜39",
            "机柜40", "机柜40", "机柜40", "机柜40", "机柜40", "机柜41", "机柜41", "机柜41", "机柜41", "机柜41", "机柜42", "机柜42", "机柜42", "机柜42", "机柜42", "机柜42", "机柜43", "机柜43", "机柜43", "机柜44", "机柜44", "机柜44",
            "机柜44", "机柜44", "机柜44", "机柜45", "机柜45", "机柜45", "机柜46", "机柜46", "机柜46", "机柜46", "机柜46", "机柜47", "机柜47", "机柜47", "机柜47", "机柜47", "机柜47", "机柜48", "机柜48", "机柜48", "机柜48", "机柜48",
            "机柜49", "机柜49", "机柜49", "机柜50", "机柜50", "机柜50", "机柜50", "机柜50"};
    private String[] outDeviceStatus = {"1", "1", "1", "0", "1", "1", "1", "1", "1", "1", "1", "0", "1", "0", "1", "1", "1", "0", "1", "1", "1", "0", "1", "1", "1", "1", "1", "1", "1", "0", "1", "1", "1", "1", "1",
            "1", "1", "0", "1", "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "0", "1", "0", "0", "1", "1", "1", "0", "0", "1", "1", "1", "1",
            "0", "1", "0", "1", "1", "1", "0", "1", "1", "1", "1", "1", "0", "0", "1", "1", "1", "1", "1", "0", "0", "1", "1", "1", "1", "0", "0", "0", "1", "1", "1", "1", "0", "0", "1", "1", "1", "1", "1", "0", "1",
            "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0", "0", "1", "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "0", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "0", "1", "1", "0", "1", "1", "0", "1", "1", "0", "1", "1", "0", "1", "0", "1", "1", "1", "1", "1", "1", "0", "1", "1", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "0", "0", "0", "0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.device_overview);//更改之后

        dbHelper = new JeffDatabaseHelper(this, "Device", null, 12);//每次需要更改版本号，有两个地方需要改
        //createDatabase();
        //addDatabase();
        //queryDatabase();

        //refreshDatabaseNew();//每次需要更改版本号
        initCabinets();//initFruits();

        //Stetho.initializeWithDefaults(this);//用于显示数据库，不能用
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

                //values.put("name", "device_1");
                //values.put("longitude", "104.067");
                //values.put("latitude", "30.658");
                //values.put("status", "1");

                for (int i = 0; i < outName.length; i++) {
                    //Log.d("data","输入一个数据data");
                    values.put("name", outName[i]);
                    values.put("longitude", outLo[i]);
                    values.put("latitude", outLa[i]);
                    values.put("status", outStatus[i]);

                    db.insert("Device", null, values);
                    values.clear();
                }
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

    private void initCabinets() {
        //queryDatabaseToList();//改成不用按钮的版本
        queryDatabaseCabinet();

        for (int i = 0; i < deviceListName.size(); i++) {
            deviceList.add(new Device(deviceListName.get(i), deviceListLo.get(i), deviceListLa.get(i), deviceListStatus.get(i)));
        }

        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //更改之后
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_device_overview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DeviceAdapter adapter = new DeviceAdapter(deviceList);
        recyclerView.setAdapter(adapter);

        //返回设备的总数
        Log.d("devices", Integer.toString(adapter.getItemCount()));
        //TextView textView = (TextView) findViewById(R.id.textView_deviceNum);
        //更改之后
        TextView textView = (TextView) findViewById(R.id.textView_deviceNum_overview);
        textView.setText(Integer.toString(adapter.getItemCount()));
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //0421 把数据库变成两个表，填入之后查询出来
    private void refreshDatabaseNew() {
        //直接删掉原表再重新加载一次
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < outCabinetName.length; i++) {
            values.put("name", outCabinetName[i]);
            values.put("longitude", outCabinetLo[i]);
            values.put("latitude", outCabinetLa[i]);
            values.put("status", outCabinetStatus[i]);
            db.insert("Cabinet", null, values);
            values.clear();
        }

        for (int i = 0;i < outDeviceName.length; i++){
            values.put("name", outDeviceName[i]);
            values.put("belong", outDeviceBelong[i]);
            values.put("status",outDeviceStatus[i]);
            db.insert("Device", null, values);
            values.clear();
        }
    }

    private void queryDatabaseCabinet() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Cabinet", null, null, null, null, null, null);//all data
        if (cursor.moveToFirst()) {
            do {
                //遍历全部对象
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") float longitude = cursor.getFloat(cursor.getColumnIndex("longitude"));
                @SuppressLint("Range") float latitude = cursor.getFloat(cursor.getColumnIndex("latitude"));
                @SuppressLint("Range") int status = cursor.getInt(cursor.getColumnIndex("status"));
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