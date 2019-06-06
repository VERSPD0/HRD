package com.dyf.hrd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LevelDao {

    @Insert
    void insert(Level level);

    @Query("DELETE FROM Level")
    void deleteAll();

    @Query("SELECT * from Level WHERE isDIY = 0 ORDER BY id ASC")
    LiveData<List<Level>> getAllLevels();

    @Query("SELECT * from Level WHERE isDIY = 1 ORDER BY id ASC")
    LiveData<List<Level>> getDIYLevels();

    @Query("SELECT * from Level WHERE id =:id")
    Level getLevel(int id);

    @Query("UPDATE Level SET step = :step where id = :id")
    void updateStep(int id, int step);

    @Query("SELECT * from Level LIMIT 1")
    Level[] getAnyLevel();
}
