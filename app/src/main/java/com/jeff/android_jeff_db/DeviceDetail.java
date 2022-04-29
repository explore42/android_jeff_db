package com.jeff.android_jeff_db;

public class DeviceDetail {
    private String name;
    private String belong;
    private String status;
    private String lo;
    private String la;
    private String info;
    private String website;

    public DeviceDetail(String name, String belong, String status, String lo, String la, String info, String website) {
        this.name = name;
        this.belong = belong;
        this.status = status;
        this.lo = lo;
        this.la = la;
        this.info = info;
        this.website = website;
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

    public String getLo() {
        return lo;
    }

    public String getLa() {
        return la;
    }

    public String getInfo() {
        return info;
    }

    public String getWebsite() {
        return website;
    }
}
