package com.example.ceibasoftwaretest.database.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface UsersDao {

    @Query("SELECT * FROM users")
    LiveData<List<Users>> loadAllUsers();

    @Insert
    void insertAll(Users[] users);

}
