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

    @Query("SELECT * from Level ORDER BY id ASC")
    LiveData<List<Level>> getAllWords();

    @Query("SELECT * from Level WHERE id =:id")
    Level getLevel(int id);
}
