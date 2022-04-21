package com.jeff.android_jeff_db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//类名是CabinetRoom，cabinetroom

@Entity(tableName = "cabinetroom")
public class CabinetRoom {
    @PrimaryKey
    public int id;

    //SQLite 中的表和列名称不区分大小写
    @ColumnInfo(name = "cabinet_name")
    public String cabinetName;

    @ColumnInfo(name = "cabinet_longitude")
    public float cabinetLongitude;

    @ColumnInfo(name = "cabinet_latitude")
    public float cabinetLatitude;

    @ColumnInfo(name = "cabinet_status")
    public int cabinetStatus;
}
