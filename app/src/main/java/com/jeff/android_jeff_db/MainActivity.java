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
    //集合嵌套,https://blog.csdn.net/qq_36098284/article/details/79936335
    private List<String> deviceListName = new ArrayList<String>();
    private List<String> deviceListLo = new ArrayList<String>();
    private List<String> deviceListLa = new ArrayList<String>();
    private List<String> deviceListStatus = new ArrayList<String>();

    private String[] outName = {"机柜1", "监控1", "音箱1", "音箱2", "机柜2", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜3", "监控1", "监控2", "音箱1", "音箱2", "机柜4", "监控1", "监控2", "音箱1", "音箱2", "机柜5", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜6", "监控1", "监控2", "监控3", "音箱1",
            "音箱2", "机柜7", "监控1", "音箱1", "音箱2", "机柜8", "监控1", "监控2", "监控3", "音箱1", "音箱2", "机柜9", "监控1", "监控2", "音箱1", "音箱2", "机柜10", "监控1", "监控2", "音箱1", "音箱2", "机柜11", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜12", "监控1", "监控2", "监控3", "音箱1", "音箱2", "机柜13", "监控1",
            "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜14", "监控1", "监控2", "监控3", "监控4", "音箱1", "音箱2", "机柜15", "监控1", "监控2", "监控3", "音箱1", "音箱2", "机柜16", "监控1", "监控2", "监控3", "监控4", "音箱1", "音箱2", "机柜17", "监控1", "监控2", "音箱1", "音箱2", "机柜18", "监控1", "监控2", "监控3", "音箱1", "音箱2",
            "机柜19", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜20", "监控1", "音箱1", "音箱2", "机柜21", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜22", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜23", "监控1", "监控2", "监控3", "音箱1", "音箱2", "机柜24", "监控1", "监控2",
            "监控3", "监控4", "音箱1", "音箱2", "机柜25", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜26", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜27", "监控1", "监控2", "监控3", "监控4", "音箱1", "音箱2", "机柜28", "监控1", "音箱1", "音箱2", "机柜29", "监控1", "监控2", "监控3", "音箱1", "音箱2",
            "机柜30", "监控1", "监控2", "监控3", "音箱1", "音箱2", "机柜31", "监控1", "监控2", "音箱1", "音箱2", "机柜32", "监控1", "监控2", "监控3", "监控4", "音箱1", "音箱2", "机柜33", "监控1", "音箱1", "音箱2", "机柜34", "监控1", "音箱1", "音箱2", "机柜35", "监控1", "监控2", "音箱1", "音箱2", "机柜36", "监控1", "音箱1", "音箱2", "机柜37", "监控1",
            "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜38", "监控1", "监控2", "音箱1", "音箱2", "机柜39", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜40", "监控1", "音箱1", "音箱2", "机柜41", "监控1", "音箱1", "音箱2", "机柜42", "监控1", "音箱1", "音箱2", "机柜43", "监控1", "监控2", "音箱1", "音箱2", "机柜44",
            "监控1", "音箱1", "音箱2", "机柜45", "监控1", "监控2", "音箱1", "音箱2", "机柜46", "监控1", "监控2", "监控3", "监控4", "监控5", "音箱1", "音箱2", "机柜47", "监控1", "监控2", "监控3", "音箱1", "音箱2", "机柜48", "监控1", "监控2", "监控3", "监控4", "音箱1", "音箱2", "机柜49", "监控1", "监控2", "音箱1", "音箱2", "机柜50", "监控1", "监控2",
            "监控3", "音箱1", "音箱2"};
    private String[] outLo = {"104.040", "104.040", "104.040", "104.040", "104.040", "104.040", "104.040", "104.010", "104.010", "104.010", "104.010", "103.991", "103.991", "103.991", "103.991", "103.991", "103.991", "104.086", "104.086", "104.086", "104.086", "104.086", "104.086", "104.086", "104.086", "103.988", "103.988",
            "103.988", "103.988", "103.988", "103.988", "104.067", "104.067", "104.067", "104.067", "104.067", "104.067", "104.116", "104.116", "104.116", "104.116", "104.006", "104.006", "104.006", "104.006", "104.068", "104.068", "104.068", "104.068", "104.068", "104.023", "104.023", "104.023", "104.023", "104.023", "104.088",
            "104.088", "104.088", "104.088", "104.088", "104.088", "104.082", "104.082", "104.082", "104.082", "104.082", "104.082", "104.082", "104.082", "104.091", "104.091", "104.091", "104.091", "104.091", "104.083", "104.083", "104.083", "104.083", "104.083", "104.083", "104.103", "104.103", "104.103", "104.103", "104.103",
            "104.103", "104.103", "103.998", "103.998", "103.998", "103.998", "103.998", "103.998", "103.998", "104.103", "104.103", "104.103", "104.103", "104.103", "104.038", "104.038", "104.038", "104.038", "104.038", "104.038", "104.038", "104.098", "104.098",
            "104.098", "104.098", "103.941", "103.941", "103.941", "103.941", "103.941", "103.941", "103.941", "104.062", "104.062", "104.062", "104.062", "104.062", "104.062", "104.023", "104.023", "104.023", "104.023", "104.023", "104.023", "104.023", "104.023",
            "104.014", "104.014", "104.014", "104.014", "104.083", "104.083", "104.083", "104.083", "104.083", "104.083", "104.129", "104.129", "104.129", "104.129", "104.129", "104.129", "104.129", "104.129", "104.076", "104.076", "104.076", "104.076", "104.076",
            "104.076", "104.076", "104.078", "104.078", "104.078", "104.078", "104.078", "104.078", "104.078", "104.078", "104.085", "104.085", "104.085", "104.085", "104.085", "104.085", "103.937", "103.937", "103.937", "103.937", "103.937", "104.126", "104.126",
            "104.126", "104.126", "104.050", "104.050", "104.050", "104.050", "104.050", "104.099", "104.099", "104.099", "104.099", "104.099", "104.099", "104.099", "104.099", "104.013", "104.013", "104.013", "104.013", "104.013", "104.001", "104.001", "104.001",
            "104.001", "104.001", "103.974", "103.974", "103.974", "103.974", "103.966", "103.966", "103.966", "103.966", "103.966", "103.966", "103.966", "103.966", "104.077", "104.077", "104.077", "104.077", "104.077", "104.077", "104.077", "104.096", "104.096",
            "104.096", "104.096", "104.096", "104.096", "104.096", "104.006", "104.006", "104.006", "104.006", "104.006", "104.006", "104.006", "104.006", "104.096", "104.096", "104.096", "104.096", "104.096", "104.064", "104.064", "104.064", "104.064", "104.064",
            "104.064", "104.064", "104.062", "104.062", "104.062", "104.062", "104.062", "104.062", "104.062", "104.067", "104.067", "104.067", "104.067", "103.978", "103.978", "103.978", "103.978", "103.978", "103.978", "103.978", "104.078", "104.078", "104.078",
            "104.078", "104.118", "104.118", "104.118", "104.118", "104.118", "104.118", "104.118", "104.118", "104.089", "104.089", "104.089", "104.089", "104.089", "104.089", "104.089", "104.089", "104.019", "104.019", "104.019", "104.019", "103.988", "103.988",
            "103.988", "103.988", "103.988", "103.988", "103.988", "104.093", "104.093", "104.093", "104.093", "104.093", "104.093", "104.093", "104.093"};
    private String[] outLa = {"30.635", "30.635", "30.635", "30.635", "30.635", "30.635", "30.635", "30.711", "30.711", "30.711", "30.711", "30.740", "30.740", "30.740", "30.740", "30.740", "30.740", "30.661", "30.661", "30.661", "30.661", "30.661",
            "30.661", "30.661", "30.661", "30.578", "30.578", "30.578", "30.578", "30.578", "30.578", "30.685", "30.685", "30.685", "30.685", "30.685", "30.685", "30.729", "30.729", "30.729", "30.729", "30.660", "30.660", "30.660", "30.660", "30.679",
            "30.679", "30.679", "30.679", "30.679", "30.661", "30.661", "30.661", "30.661", "30.661", "30.746", "30.746", "30.746", "30.746", "30.746", "30.746", "30.607", "30.607", "30.607", "30.607", "30.607", "30.607", "30.607", "30.607", "30.729",
            "30.729", "30.729", "30.729", "30.729", "30.573", "30.573", "30.573", "30.573", "30.573", "30.573", "30.753", "30.753", "30.753", "30.753", "30.753", "30.753", "30.753", "30.640", "30.640", "30.640", "30.640", "30.640", "30.640", "30.640",
            "30.718", "30.718", "30.718", "30.718", "30.718", "30.661", "30.661", "30.661", "30.661", "30.661", "30.661", "30.661", "30.723", "30.723", "30.723", "30.723", "30.666", "30.666", "30.666", "30.666", "30.666", "30.666", "30.666", "30.631",
            "30.631", "30.631", "30.631", "30.631", "30.631", "30.658", "30.658", "30.658", "30.658", "30.658", "30.658", "30.658", "30.658", "30.607", "30.607", "30.607", "30.607", "30.576", "30.576", "30.576", "30.576", "30.576", "30.576", "30.704",
            "30.704", "30.704", "30.704", "30.704", "30.704", "30.704", "30.704", "30.625", "30.625", "30.625", "30.625", "30.625", "30.625", "30.625", "30.596", "30.596", "30.596", "30.596", "30.596", "30.596", "30.596", "30.596", "30.648", "30.648",
            "30.648", "30.648", "30.648", "30.648", "30.578", "30.578", "30.578", "30.578", "30.578", "30.753", "30.753", "30.753", "30.753", "30.676", "30.676", "30.676", "30.676", "30.676", "30.601", "30.601", "30.601", "30.601", "30.601", "30.601",
            "30.601", "30.601", "30.602", "30.602", "30.602", "30.602", "30.602", "30.689", "30.689", "30.689", "30.689", "30.689", "30.710", "30.710", "30.710", "30.710", "30.670", "30.670", "30.670", "30.670", "30.670", "30.670", "30.670", "30.670",
            "30.584", "30.584", "30.584", "30.584", "30.584", "30.584", "30.584", "30.579", "30.579", "30.579", "30.579", "30.579", "30.579", "30.579", "30.695", "30.695", "30.695", "30.695", "30.695", "30.695", "30.695", "30.695", "30.671", "30.671",
            "30.671", "30.671", "30.671", "30.581", "30.581", "30.581", "30.581", "30.581", "30.581", "30.581", "30.593", "30.593", "30.593", "30.593", "30.593", "30.593", "30.593", "30.729", "30.729", "30.729", "30.729", "30.625", "30.625", "30.625",
            "30.625", "30.625", "30.625", "30.625", "30.592", "30.592", "30.592", "30.592", "30.636", "30.636", "30.636", "30.636", "30.636", "30.636", "30.636", "30.636", "30.730", "30.730", "30.730", "30.730", "30.730", "30.730", "30.730", "30.730",
            "30.681", "30.681", "30.681", "30.681", "30.627", "30.627", "30.627", "30.627", "30.627", "30.627", "30.627", "30.594", "30.594", "30.594", "30.594", "30.594", "30.594", "30.594", "30.594"};
    private String[] outStatus = {"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //更改之后
        setContentView(R.layout.device_overview);

        dbHelper = new JeffDatabaseHelper(this, "Device", null, 4);//每次需要更改版本号
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

    private void initDevices() {
        //for (int i = 0; i < 5; i++) {//让数据充满屏幕
        //    Device device1 = new Device("device_1", "100.123", "456.789", "1");
        //    deviceList.add(device1);
        //}

        queryDatabaseToList();
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
}