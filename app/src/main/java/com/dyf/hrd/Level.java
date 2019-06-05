package com.dyf.hrd;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Arrays;

@Entity
public class Level {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "data")
    private String data;

    @ColumnInfo(name = "step")
    private int step;

    static boolean flag = true;

    public Level(@NonNull String data) {
        if (flag) {
            this.id = 0;
            this.flag = false;
        }
        this.data = data;
        this.step = 0;

    }

    public String getData() {
        return this.data;
    }

    public int getId() {
        return this.id;
    }

    public boolean resolved() {
        if (this.step > 0)
            return true;
        else
            return false;
    }

    public int getStep() { return this.step; }

    public void setStep(int step) { this.step = step; }

    public void setId(int id) { this.id = id; }
}