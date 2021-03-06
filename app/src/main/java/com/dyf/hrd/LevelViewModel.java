package com.dyf.hrd;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class LevelViewModel extends AndroidViewModel {
    private LiveData<List<Level>> mAllLevels;
    private LiveData<List<Level>> mDIYLevels;
    private LevelRepository mRepository;


    public LevelViewModel(Application application) {
        super(application);
        mRepository = new LevelRepository(application);
        mAllLevels = mRepository.getmAllLevels();
        mDIYLevels = mRepository.getmDIYLevels();
    }

    LiveData<List<Level>> getmAllLevels() { return mAllLevels; }

    LiveData<List<Level>> getmDIYLevels() { return mDIYLevels; }

    public void insert(Level level) { mRepository.insert(level); }

    public void updateStep(int id, int step) {
        int _step = mRepository.getLevel(id).getStep();
        if (_step == 0 || (_step != 0 && step < _step))
            mRepository.updateStep(id, step);
    }

    public int[][] getData(int id) {
        Level level = mRepository.getLevel(id);
        return string2Array(level.getData());
    }

    private int[][] string2Array(String string) {
        int data[][] = new int[10][3];
        string = string.replace("[", "");
        string = string.substring(0, string.length() - 2);
        String s[] = string.split("],");
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].trim();
            String sn[] = s[i].split(", ");
            data[i] = new int[sn.length];
            for (int j = 0; j < sn.length; j++) {
                data[i][j] = Integer.valueOf(sn[j]);
            }
        }
        return data;
    }
}
