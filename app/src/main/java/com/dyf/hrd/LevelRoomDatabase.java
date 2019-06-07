package com.dyf.hrd;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Arrays;

@Database(entities = {Level.class}, version = 2, exportSchema = false)
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
                        {0, 1, 1}, {1, 2, 2}, {3, 1, 2}, {4, 1, 1},
                        {8, 1, 1}, {9, 2, 1}, {11, 1, 2}, {12, 1, 1},
                        {13, 2, 1}, {17, 2, 1}
                },
                {
                        {0, 1, 2}, {1, 2, 2}, {3, 1, 1}, {8, 1, 2},
                        {9, 2, 1}, {11, 1, 2}, {13, 1, 1}, {14, 1, 1},
                        {16, 1, 1}, {19, 1, 1}
                },
                {
                        {0, 1, 1}, {1, 2, 2}, {3, 1, 2}, {4, 1, 1},
                        {8, 1, 2}, {9, 2, 1}, {11, 1, 1}, {13, 2, 1},
                        {15, 1, 1}, {17, 2, 1}
                },
                {
                        {0, 1, 1}, {3, 1, 1}, {4, 1, 1}, {7, 1, 1},
                        {1, 2, 2}, {8, 1, 2}, {10, 2, 1}, {14, 2, 1},
                        {16, 2, 1}, {18, 2, 1}
                },
                {
                        {0, 2, 2}, {2, 1, 2}, {3, 1, 2}, {8, 2, 1},
                        {10, 1, 1}, {11, 1, 1}, {12, 1, 2}, {13, 1, 2},
                        {14, 1, 1}, {15, 1, 1}
                },
                {
                        {0, 2, 1}, {2, 2, 2}, {4, 2, 1}, {8, 2, 1},
                        {10, 2, 1}, {12, 1, 1}, {14, 1, 1}, {15, 1, 2},
                        {16, 1, 1}, {18, 1, 1}
                },
                {
                        {0, 1, 2}, {1, 2, 2}, {3, 1, 1}, {7, 1, 1},
                        {8, 2, 1}, {10, 1, 1}, {11, 1, 1}, {12, 1, 2},
                        {13, 2, 1}, {15, 1, 2}
                },
                {
                        {0, 2, 2}, {2, 1, 1}, {3, 1, 2}, {6, 1, 1},
                        {8, 2, 1}, {10, 2, 1}, {12, 2, 1}, {14, 2, 1},
                        {16, 1, 1}, {19, 1, 1}
                },
                {
                        {0, 1, 2}, {1, 1, 2}, {2, 1, 2}, {3, 1, 2},
                        {9, 2, 2}, {12, 1, 1}, {15, 1, 1}, {16, 2, 1},
                        {18, 1, 1}, {19, 1, 1}
                },
                {
                        {1, 1, 1}, {2, 1, 1}, {3, 1, 1}, {4, 2, 2},
                        {6, 1, 2}, {7, 1, 2}, {12, 2, 1}, {14, 1, 2},
                        {15, 1, 1}, {16, 2, 1}
                },
                {
                        {0, 1, 2}, {1, 2, 2}, {3, 1, 2}, {8, 1, 2},
                        {9, 2, 1}, {11, 1, 1}, {13, 2, 1}, {15, 1, 1},
                        {16, 1, 1}, {19, 1, 1}
                },
                {
                        {0, 1, 2}, {1, 2, 2}, {3, 1, 1}, {7, 1, 1},
                        {8, 1, 2}, {9, 2, 1}, {11, 1, 2}, {13, 2, 1},
                        {16, 1, 1}, {19, 1, 1}
                },
                {
                        {0, 1, 2}, {1, 2, 2}, {3, 1, 2}, {8, 1, 1},
                        {9, 1, 2}, {11, 1, 1}, {12, 1, 1}, {15, 1, 1},
                        {16, 2, 1}, {18, 2, 1}
                }
        };

        PopulateDbAsync(LevelRoomDatabase db) {
            mDao = db.levelDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            if (mDao.getAnyLevel().length < 1) {
                for (int i = 0; i < data.length; i++) {
                    Level level = new Level(Arrays.deepToString(data[i]));
                    mDao.insert(level);
                }
            }
            return null;
        }
    }
}
