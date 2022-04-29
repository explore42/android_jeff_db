package com.jeff.android_jeff_db;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    //https://developer.android.google.cn/guide/webapps/webview?hl=zh-cn
    //Js 和 Java 之间的接口
    private Context mContext;
    private float Lo;
    private float La;
    private String website;

    public WebAppInterface(Context c,float Lo,float La,String website) {
        mContext = c;
        this.Lo = Lo;
        this.La = La;
        this.website = website;
    }//传入上下文

    @JavascriptInterface
    public float getLo(){
        return Lo;
    }

    @JavascriptInterface
    public float getLa(){
        return La;
    }

    @JavascriptInterface
    public String getWebsite() {
        return website;
    }
}
