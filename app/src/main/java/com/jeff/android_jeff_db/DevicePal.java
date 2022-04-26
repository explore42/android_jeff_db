package com.jeff.android_jeff_db;

import org.litepal.crud.LitePalSupport;

public class DevicePal extends LitePalSupport {
    private int id;
    private String name;
    private String belong;
    private String status;
    private String info;
    private String longitude;
    private String latitude;
    private String website;

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
