package com.example.ceibasoftwaretest.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ceibasoftwaretest.database.data.Users;
import com.example.ceibasoftwaretest.database.data.UsersDao;
import com.example.ceibasoftwaretest.database.data.UsersData;

@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "dbcebia.db";
    private static AppDatabase sIntance;

    public abstract UsersDao usersDao();

    public static AppDatabase getInstance(final Context context){
        if (sIntance == null){
            synchronized (LOCK){
                sIntance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, AppDatabase.DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            AppExecutors.getInstance().diskIo()
                                .execute(() -> getInstance(context).usersDao()
                                    .insertAll(UsersData
                                    .popuateUsersData()));
                        }
                    }).build();
            }
        }
        return sIntance;
    };

}
