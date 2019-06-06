package com.dyf.hrd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class LevelRepository {
    private LevelDao mLevelDao;
    private LiveData<List<Level>> mAllLevels;
    private LiveData<List<Level>> mDIYLevels;

    private static class insertAsyncTask extends AsyncTask<Level, Void, Void> {

        private LevelDao mAsyncTaskDao;

        insertAsyncTask(LevelDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Level... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getAsyncTask extends AsyncTask<Integer, Void, Level> {

        private LevelDao mAsyncTaskDao;

        getAsyncTask(LevelDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Level doInBackground(final Integer... params) {
            return mAsyncTaskDao.getLevel(params[0]);
        }
    }

    LevelRepository(Application application) {
        LevelRoomDatabase db = LevelRoomDatabase.getDatabase(application);
        mLevelDao = db.levelDao();
        mAllLevels = mLevelDao.getAllLevels();
        mDIYLevels = mLevelDao.getDIYLevels();
    }

    LiveData<List<Level>> getmAllLevels() {
        return mAllLevels;
    }

    LiveData<List<Level>> getmDIYLevels() { return mDIYLevels; }

    Level getLevel(int id) {
        try {
            return new getAsyncTask(mLevelDao).execute(id).get();
        } catch (Exception e) {
            return new Level("");
        }
    }

    public void insert (Level level) {
        new insertAsyncTask(mLevelDao).execute(level);
    }
}
