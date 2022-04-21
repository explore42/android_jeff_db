package com.jeff.android_jeff_db;

public class DeviceDetail {
    private String name;
    private String belong;
    private String status;

    public DeviceDetail(String name,String belong, String status){
        this.name = name;
        this.belong = belong;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getBelong() {
        return belong;
    }

    public String getStatus() {
        return status;
    }
}
