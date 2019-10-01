package com.example.ceibasoftwaretest.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.ceibasoftwaretest.MyApplication;
import com.example.ceibasoftwaretest.database.data.User.Users;
import com.example.ceibasoftwaretest.database.AppDatabase;
import com.example.ceibasoftwaretest.database.data.User.UsersDao;

import java.util.List;

public class UsersRepository {

    private LiveData<List<Users>> usersList;
    private UsersDao usersDao;

    public UsersRepository() {
        AppDatabase appDatabase =  AppDatabase.getInstance(MyApplication.getInstance());
        usersDao = appDatabase.usersDao();
        usersList = appDatabase.usersDao().loadAllUsers();
    }

    public void insert(Users users){
        new InsertUsersAsyncTask(usersDao).execute();
    }

    public LiveData<List<Users>> getUsersList(){
        return usersList;
    }

    private static class InsertUsersAsyncTask extends AsyncTask<Users, Void, Context>{

        private UsersDao usersDao;

        private InsertUsersAsyncTask(UsersDao usersDao){
            this.usersDao = usersDao;
        }


        @Override
        protected Context doInBackground(Users... users) {
            usersDao.insertAll(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Context context) {
            super.onPostExecute(context);
            Toast.makeText(context, "COMPLETED", Toast.LENGTH_LONG).show();
        }
    }
}
