package com.example.ceibasoftwaretest.database.network;

import com.example.ceibasoftwaretest.database.data.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIinterface {

    @GET("users")
    Call<List<Users>> getUsers();
}
