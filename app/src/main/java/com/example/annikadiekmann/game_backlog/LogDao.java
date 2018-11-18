package com.example.annikadiekmann.game_backlog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LogDao {

    @Query("SELECT * FROM log")
    public List<Log> getAllLogs();

    @Insert
    public void insertLogs(Log logs);

    @Delete
    public void deleteLogs(Log logs);

    @Update
    public void updateLogs(Log logs);


}
