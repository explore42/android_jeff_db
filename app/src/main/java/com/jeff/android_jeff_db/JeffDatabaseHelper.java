package com.jeff.android_jeff_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class JeffDatabaseHelper extends SQLiteOpenHelper {
    //建立一个表
    public static final String CREATE_DEVICE = "create table device(" +
            "id integer primary key autoincrement," +
            "name text," +
            "longitude real," +
            "latitude real," +
            "status integer)";
    private Context mContext;

    public JeffDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //存在数据库之后，onCreate不会再次执行
        db.execSQL(CREATE_DEVICE);
        Toast.makeText(mContext, "Create table succeed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //会在这里执行刷新数据库
        db.execSQL("drop table if exists Device");//删除旧数据库，需要传入一个更大的版本号
        onCreate(db);
    }
}
