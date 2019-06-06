package com.dyf.hrd;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;


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

    @ColumnInfo(name = "isDIY")
    private boolean isDIY;

    @ColumnInfo(name = "title")
    private String title;

    static boolean flag = true;

    public Level(@NonNull String data) {
        if (flag) {
            this.id = 0;
            this.flag = false;
        }
        this.data = data;
        this.step = 0;
        this.isDIY = false;
    }

    @Ignore
    public Level(@NonNull String data, @NonNull String title) {
        this.data = data;
        this.title = title;
        this.step = 0;
        this.isDIY = true;
        Log.d("======diy", "======diy");

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

    public String getTitle() { return this.title; }

    public boolean isDIY() { return this.isDIY; }

    public void setDIY(boolean flag) { this.isDIY = flag; }

    public void setTitle(String title) { this.title = title; }
}