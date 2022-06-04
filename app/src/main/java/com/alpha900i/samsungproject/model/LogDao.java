package com.alpha900i.samsungproject.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class LogDao {
    @Insert
    public abstract void insertLog(LogEntry logEntry);

    @Query("SELECT * FROM log")
    public abstract List<LogEntry> getAllLogs();

    @Query("SELECT * FROM log")
    public abstract LiveData<List<LogEntry>> getAllLogsLive();

    @Query("SELECT COUNT(*) FROM log")
    public abstract int getLogCount();

    @Query("DELETE FROM log")
    public abstract void deleteAll();
}
