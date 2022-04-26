package com.jeff.android_jeff_db;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CabinetRequest {
    private String urlString;
    List<CabinetPal> cabinetPals = new ArrayList<>();//0426 存放读到的数据

    public CabinetRequest(String urlString) {//初始化
        this.urlString = urlString;
    }

    public List<CabinetPal> sendRequestWithOKHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(urlString).header("Connection", "close").build();
                    Response response = client.newCall(request).execute();//同步方式
                    String responseData = response.body().string();
                    if (urlString.equals("http://10.0.2.2/cabinet.xml") || urlString.equals("http://192.168.18.133/cabinet.xml")) {
                        Log.e("OKHTTP", "开始解析cabinet");
                        parseXMLWithPullCabinet(responseData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("OKHTTP cabinet", "请求错误");
                }
            }
        }).start();

        return cabinetPals;
    }

    private void parseXMLWithPullCabinet(String xmlData) {//解析服务器返回的数据
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));//将得到的数据传进去
            int eventType = xmlPullParser.getEventType();//得到当前的解析事件

            String name = "";
            String lo = "";
            String la = "";
            String status = "";
            String info = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();//得到当前结点的名字
                switch (eventType) {
                    case XmlPullParser.START_TAG: {//开始解析某个结点
                        if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();//获得结点的具体内容
                        } else if ("lo".equals(nodeName)) {
                            lo = xmlPullParser.nextText();
                        } else if ("la".equals(nodeName)) {
                            la = xmlPullParser.nextText();
                        } else if ("status".equals(nodeName)) {
                            status = xmlPullParser.nextText();
                        } else if ("info".equals(nodeName)) {
                            info = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {//完成解析某个结点
                        if ("cabinet".equals(nodeName)) {
                            CabinetPal cabinetPal = new CabinetPal();
                            cabinetPal.setName(name);
                            cabinetPal.setLongitude(lo);
                            cabinetPal.setLatitude(la);
                            cabinetPal.setStatus(status);
                            cabinetPal.setInfo(info);
                            cabinetPals.add(cabinetPal);
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
            Log.e("cabinet","解析失败");
        }
    }
}
