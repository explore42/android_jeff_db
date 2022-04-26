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

import org.litepal.LitePal;

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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //4022 用服务器更新数据
    private ServerRequest serverRequest1;
    private ServerRequest serverRequest2;
    private int versionID = 15;//版本号
    //private String urlCabinet = "http://10.0.2.2/cabinet.xml";//对于模拟器的本地地址
    //private String urlDevice = "http://10.0.2.2/device.xml";
    private String urlCabinet = "http://192.168.18.133/cabinet.xml";//给手机访问用的
    private String urlDevice = "http://192.168.18.133/device.xml";

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.device_overview);//更改之后

        serverRequest1 = new ServerRequest(urlCabinet);//实例化
        serverRequest2 = new ServerRequest(urlDevice);

        List<CabinetPal> cabinetPals = LitePal.findAll(CabinetPal.class);//先查一下数据库里面有没有
        if(cabinetPals.size() == 0) {
            getDataFromServer();//没有，就更新数据库
        }
        initListFromServer();//要是有，就直接展示UI
        deleteDBPal();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void getDataFromServer() {
        int maxRequest = 5;//最多请求5次
        int requestNum = 0;

        ArrayList<ArrayList<String>> test1 = new ArrayList<ArrayList<String>>();//注意类型
        if (test1.size() == 0) {
            test1 = serverRequest1.sendRequestWithOKHttp();//里面有一个单独的线程

            while ((test1.size() == 0) && requestNum < maxRequest) {
                test1 = serverRequest1.sendRequestWithOKHttp();
                Log.e("requestWebToMainCabinet", "多请求了机柜 一次");
                requestNum++;
            }
        }
        Log.e("requestWebToMainCabinet", Integer.toString(test1.size()));
        Log.e("requestWebToMainCabinet", Integer.toString(test1.get(0).size()));
        requestNum = 0;


        ArrayList<ArrayList<String>> test2 = new ArrayList<ArrayList<String>>();
        if (test2.size() == 0) {
            test2 = serverRequest2.sendRequestWithOKHttp();//里面有一个单独的线程

            while ((test2.size() == 0) && requestNum < maxRequest) {
                test2 = serverRequest2.sendRequestWithOKHttp();
                Log.e("requestWebToMainDevice", "多请求了 设备 一次");
                requestNum++;
            }
        }
        Log.e("requestWebToMainDevice", Integer.toString(test2.size()));
        Log.e("requestWebToMainDevice", Integer.toString(test2.get(0).size()));

        //更新数据
        //Log.e("检查数据", test2.get(0).get(2));
        updateDBFromServer(test1, test2);
    }

    private void updateDBFromServer(ArrayList<ArrayList<String>> cabinetString, ArrayList<ArrayList<String>> deviceString) {
        List<CabinetPal> cabinetPals = new ArrayList<>();
        List<DevicePal> devicePals = new ArrayList<>();

        for (int i = 0; i < cabinetString.get(0).size(); i++) {
            CabinetPal cabinetPal = new CabinetPal();
            cabinetPal.setName(cabinetString.get(0).get(i));
            cabinetPal.setLongitude(cabinetString.get(1).get(i));
            cabinetPal.setLatitude(cabinetString.get(2).get(i));
            cabinetPal.setStatus(cabinetString.get(3).get(i));
            cabinetPals.add(cabinetPal);
        }
        LitePal.saveAll(cabinetPals);

        for (int i = 0; i < deviceString.get(0).size(); i++) {
            DevicePal devicePal = new DevicePal();
            devicePal.setName(deviceString.get(0).get(i));
            devicePal.setBelong(deviceString.get(1).get(i));
            devicePal.setStatus(deviceString.get(2).get(i));
            devicePals.add(devicePal);
        }
        LitePal.saveAll(devicePals);

        List<DevicePal> devicePal1 = LitePal.findAll(DevicePal.class);
        Log.e("Pal数据库", "获取成功");
        Log.e("Pal数据库", Integer.toString(devicePal1.size()));

        List<CabinetPal> cabinetPals1 = LitePal.findAll(CabinetPal.class);
        Log.e("Pal数据库", "获取成功");
        Log.e("Pal数据库", Integer.toString(cabinetPals1.size()));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //0426 Pal db
    private void createDBPal() {
        Button createDB = findViewById(R.id.button_create_palDB);
        createDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();//建立数据库

                /*
                DevicePal devicePal = new DevicePal();
                devicePal.setName("测试设备");
                devicePal.setBelong("测试机柜1");
                devicePal.setStatus("1");
                devicePal.save();

                List<DevicePal> devicePal2 = LitePal.findAll(DevicePal.class);
                Log.e("Pal数据库", "获取成功");
                Log.e("Pal数据库", Integer.toString(devicePal2.size()));

                LitePal.deleteAll(DevicePal.class);
                List<DevicePal> devicePal3 = LitePal.findAll(DevicePal.class);
                Log.e("Pal数据库", "获取成功");
                Log.e("Pal数据库", Integer.toString(devicePal3.size()));*/
            }
        });
    }

    private void deleteDBPal() {
        Button deleteDB = findViewById(R.id.button_delete_db_pal);
        deleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.deleteAll(DevicePal.class);
                LitePal.deleteAll(CabinetPal.class);
            }
        });
    }

    private void initListFromServer() {
        //先查询数据库
        List<CabinetPal> cabinetPals = LitePal.findAll(CabinetPal.class);

        for (int i = 0; i < cabinetPals.size(); i++) {
            deviceList.add(new Device(cabinetPals.get(i).getName(), cabinetPals.get(i).getLongitude(), cabinetPals.get(i).getLatitude(), cabinetPals.get(i).getStatus()));
        }

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

        //刷新adapter?
        adapter.notifyDataSetChanged();
    }
}