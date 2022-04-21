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

    public WebAppInterface(Context c,float Lo,float La) {
        mContext = c;
        this.Lo = Lo;
        this.La = La;
    }//传入上下文

    @JavascriptInterface
    public float getLo(){
        return Lo;
    }

    @JavascriptInterface
    public float getLa(){
        return La;
    }
}
