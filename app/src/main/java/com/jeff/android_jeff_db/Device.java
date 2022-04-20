package com.jeff.android_jeff_db;

public class Device {
    private String name;
    private String longitude;
    private String latitude;
    private String status;

    public Device(String name,String longitude,String latitude, String status){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getStatus() {
        return status;
    }
}
