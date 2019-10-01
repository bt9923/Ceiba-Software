package com.example.ceibasoftwaretest.repositories;

import androidx.lifecycle.LiveData;

import com.example.ceibasoftwaretest.MyApplication;
import com.example.ceibasoftwaretest.database.data.Users;
import com.example.ceibasoftwaretest.database.AppDatabase;

import java.util.List;

public class UsersRepository {

    private LiveData<List<Users>> usersList;

    public UsersRepository() {
        usersList = AppDatabase.getInstance(MyApplication.getInstance())
                .usersDao().loadAllUsers();
    }

    public LiveData<List<Users>> getUsersList(){
        return usersList;
    }
}
