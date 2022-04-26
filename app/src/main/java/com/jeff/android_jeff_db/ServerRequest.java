package com.jeff.android_jeff_db;

import android.util.Log;

import androidx.annotation.NonNull;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServerRequest {
    private String urlString;

    static class CabinetClock {//存放读到的数据
        public static final Object lock = new Object();//设置加锁的实例
        public static ArrayList<String> serverNameClock = new ArrayList<String>();
        public static ArrayList<String> serverLoClock = new ArrayList<String>();
        public static ArrayList<String> serverLaClock = new ArrayList<String>();
        public static ArrayList<String> serverStatusClock = new ArrayList<String>();
        public static ArrayList<ArrayList<String>> serverTotalClock = new ArrayList<ArrayList<String>>();//把上述几个List合并
    }

    static class DeviceClock {//存放读到的数据
        public static final Object lock = new Object();//设置加锁的实例
        public static ArrayList<String> serverNameClock = new ArrayList<String>();
        public static ArrayList<String> serverBelongClock = new ArrayList<String>();
        public static ArrayList<String> serverStatusClock = new ArrayList<String>();
        public static ArrayList<ArrayList<String>> serverTotalClock = new ArrayList<ArrayList<String>>();//把上述几个List合并
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ServerRequest(String urlString) {//初始化
        this.urlString = urlString;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //发起网络请求,可以从外部调用
    //传入对应的url地址
    public ArrayList<ArrayList<String>> sendRequestWithOKHttp() {// 先开启了一个子线程，然后在子线程里使用OkHttp发出一条HTTP请求
        //Thread t = new Thread(new MyRunnable());
        //t.start();

        //使用有结果的办法
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ExecutorService executor = Executors.newCachedThreadPool();//创建线程池

        Callable<ArrayList<ArrayList<String>>> task = new MyCallable(urlString);//开启子线程
        Future<ArrayList<ArrayList<String>>> future = executor.submit(task);// 提交任务并获得Future:

        // 从Future获取异步执行返回的结果:
        try {
            result = future.get(); // 可能阻塞
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("OKHTTP", "错误");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("OKHTTP", "错误");
        }
        Log.e("OKHTTP", Integer.toString(result.size()));
        return (result);
    }

    class MyCallable implements Callable<ArrayList<ArrayList<String>>> {//返回异步结果
        ArrayList<ArrayList<String>> serverReturn = new ArrayList<ArrayList<String>>();

        public String url;

        public MyCallable(String url) {
            this.url = url;
        }

        @Override
        public ArrayList<ArrayList<String>> call() throws Exception {
            try {
                OkHttpClient client = new OkHttpClient();
                //最终通过这个build方法展示出这个request，各种办法就包装在request中
                Request request = new Request.Builder().url(url).header("Connection", "close").build();

                Response response = client.newCall(request).execute();//同步方式
                String responseData = response.body().string();

                if (url.equals("http://10.0.2.2/cabinet.xml") || url.equals("http://192.168.18.133/cabinet.xml")) {
                    Log.e("OKHTTP", "开始解析cabinet");
                    serverReturn = parseXMLWithPullCabinet(responseData);
                } else if (url.equals("http://10.0.2.2/device.xml") || url.equals("http://192.168.18.133/device.xml")) {
                    Log.e("OKHTTP", "开始解析device");
                    serverReturn = parseXMLWithPullDevice(responseData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("OKHTTP", "request 异常");
            }
            return (serverReturn);
        }
    }

    private ArrayList<ArrayList<String>> parseXMLWithPullCabinet(String xmlData) {
        //解析服务器返回的数据
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));//将得到的数据传进去
            int eventType = xmlPullParser.getEventType();//得到当前的解析事件

            String name = "";
            String lo = "";
            String la = "";
            String status = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();//得到当前结点的名字
                switch (eventType) {
                    //开始解析某个结点
                    case XmlPullParser.START_TAG: {//获得结点的具体内容
                        if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("lo".equals(nodeName)) {
                            lo = xmlPullParser.nextText();
                        } else if ("la".equals(nodeName)) {
                            la = xmlPullParser.nextText();
                        } else if ("status".equals(nodeName)) {
                            status = xmlPullParser.nextText();
                        }
                        break;
                    }

                    //完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("cabinet".equals(nodeName)) {
                            //Log.d("xml", "name is " + name);
                            //serverName.add(name);
                            synchronized (CabinetClock.lock) {//先加锁，后写入
                                CabinetClock.serverNameClock.add(name);
                                CabinetClock.serverLoClock.add(lo);
                                CabinetClock.serverLaClock.add(la);
                                CabinetClock.serverStatusClock.add(status);
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (CabinetClock.lock){
            CabinetClock.serverTotalClock.add(CabinetClock.serverNameClock);
            CabinetClock.serverTotalClock.add(CabinetClock.serverLoClock);
            CabinetClock.serverTotalClock.add(CabinetClock.serverLaClock);
            CabinetClock.serverTotalClock.add(CabinetClock.serverStatusClock);
        }
        return (CabinetClock.serverTotalClock);
    }

    private ArrayList<ArrayList<String>> parseXMLWithPullDevice(String xmlData) {//解析服务器返回的数据
        //Log.e("OKHTTP", "开始解析Device");
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));//将得到的数据传进去
            int eventType = xmlPullParser.getEventType();//得到当前的解析事件

            String name = "";
            String belong = "";
            String status = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();//得到当前结点的名字
                switch (eventType) {
                    //开始解析某个结点
                    case XmlPullParser.START_TAG: {//获得结点的具体内容
                        if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("belong".equals(nodeName)) {
                            belong = xmlPullParser.nextText();
                        } else if ("status".equals(nodeName)) {
                            status = xmlPullParser.nextText();
                        }
                        break;
                    }

                    //完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("device".equals(nodeName)) {
                            synchronized (DeviceClock.lock) {//先加锁，后写入
                                DeviceClock.serverNameClock.add(name);
                                DeviceClock.serverBelongClock.add(belong);
                                DeviceClock.serverStatusClock.add(status);
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (DeviceClock.lock){
            DeviceClock.serverTotalClock.add(DeviceClock.serverNameClock);
            DeviceClock.serverTotalClock.add(DeviceClock.serverBelongClock);
            DeviceClock.serverTotalClock.add(DeviceClock.serverStatusClock);
        }
        return (DeviceClock.serverTotalClock);
    }
}