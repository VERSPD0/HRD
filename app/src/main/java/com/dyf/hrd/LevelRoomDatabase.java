package com.dyf.hrd;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Arrays;

@Database(entities = {Level.class}, version = 1, exportSchema = false)
public abstract class LevelRoomDatabase extends RoomDatabase {
    public abstract LevelDao levelDao();
    private static LevelRoomDatabase INSTANCE;

    static LevelRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LevelRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LevelRoomDatabase.class, "level_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LevelDao mDao;
        int data[][][] = {
                {
                    {0, 1, 1}, {3, 1, 1}, {4, 1, 1}, {7, 1, 1},
                    {12, 1, 2}, {13, 1, 2}, {14, 1, 2}, {15, 1, 2},
                    {9, 2, 1}, {1, 2, 2}
                },
                {
                    {0, 1, 2}, {1, 1, 2}, {2, 1, 2}, {3, 1, 1},
                    {7, 1, 1}, {8, 1, 2}, {9, 1, 1}, {10, 2, 1},
                    {16, 1, 1}, {14, 2, 2}
                }
        };

        PopulateDbAsync(LevelRoomDatabase db) {
            mDao = db.levelDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            for (int i = 0; i < data.length; i++) {
                Level level = new Level(Arrays.deepToString(data[i]));
                mDao.insert(level);
            }
            return null;
        }
    }
}
