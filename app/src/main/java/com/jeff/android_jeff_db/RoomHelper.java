package com.jeff.android_jeff_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomHelper {
    @Query("SELECT * FROM cabinetroom")
    List<CabinetRoom> getAll();

    @Query("SELECT * FROM cabinetroom WHERE id IN (:cabinetIds)")
    List<CabinetRoom> loadAllByIds(int[] cabinetIds);

    @Query("SELECT * FROM cabinetroom WHERE cabinet_status LIKE :1 LIMIT 1")
    CabinetRoom findByStatus(Integer statusNum);

    @Insert
    void insertSingleCabinet(CabinetRoom cabinet);

    @Delete
    void delete(CabinetRoom cabinet);
}
