package com.example.ceibasoftwaretest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ceibasoftwaretest.database.data.User.Users;
import com.example.ceibasoftwaretest.repositories.UsersRepository;

import java.util.List;

public class UsersFragmentViewModel extends ViewModel {

    private UsersRepository usersRepository;

    private LiveData<List<Users>> mUsersList;

    public UsersFragmentViewModel(){
        usersRepository = new UsersRepository();
        mUsersList = usersRepository.getUsersList();
    }

    public LiveData<List<Users>> getAllUsers(){
        return mUsersList;
    }
}
