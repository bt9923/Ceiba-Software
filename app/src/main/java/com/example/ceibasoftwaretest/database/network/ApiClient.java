package com.example.ceibasoftwaretest.database.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static String base_url = "https://jsonplaceholder.typicode.com/";

    public static Retrofit getClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static APIinterface apiInterface(){
        return getClient().create(APIinterface.class);
    }
}
