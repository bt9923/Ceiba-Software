package com.example.ceibasoftwaretest.database.network;

import com.example.ceibasoftwaretest.database.data.Post.Post;
import com.example.ceibasoftwaretest.database.data.User.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {

    @GET("users")
    Call<List<Users>> getUsers();

    @GET("posts")
    Call<List<Post>> getPostById(@Query("userId") Integer userId);
}
