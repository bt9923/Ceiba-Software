package com.example.ceibasoftwaretest.database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ceibasoftwaretest.database.data.User.Users;
import com.example.ceibasoftwaretest.database.data.User.UsersDao;
import com.example.ceibasoftwaretest.database.network.ApiClient;
import com.example.ceibasoftwaretest.ui.UsersList.UsersFragment;
import com.example.ceibasoftwaretest.ui.ViewDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "dbcebia.db";
    private static AppDatabase sIntance;
    private static Context mContext;
    ViewDialog viewDialog;

    public abstract UsersDao usersDao();

    public static AppDatabase getInstance(final Context context){
        if (sIntance == null){
            synchronized (LOCK){
                sIntance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, AppDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build();
            }
        }

        mContext = context;
        return sIntance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(sIntance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{

        private UsersDao usersDao;

        private PopulateDBAsyncTask(AppDatabase appDatabase){
            usersDao = appDatabase.usersDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Call<List<Users>> call = ApiClient.apiInterface().getUsers();
            call.enqueue(new retrofit2.Callback<List<Users>>() {
                @Override
                public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {

//                    UsersFragment.showCustomLoadingDialog();

                    if (response.isSuccessful()){

                        assert response.body() != null;
                        Users users = new Users();
                        for (int i = 0; i < response.body().size(); i++){
                            users.setId(response.body().get(i).getId());
                            users.setName(response.body().get(i).getName());
                            users.setPhone(response.body().get(i).getPhone());
                            users.setEmail(response.body().get(i).getEmail());

                            usersDao.insertAll(users);
                        }

                        UsersFragment.hideCustomLoadingDialog();

                    }else{
                        Toast.makeText(mContext, "An error occurred " + response.code(), Toast.LENGTH_LONG).show();
                        UsersFragment.hideCustomLoadingDialog();
                    }

                }

                @Override
                public void onFailure(Call<List<Users>> call, Throwable t) {
                    Toast.makeText(mContext, "An error occurred " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    UsersFragment.hideCustomLoadingDialog();
                }
            });

            return null;
        }
    }

}
