package com.example.ceibasoftwaretest;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance == null){
            instance = this;
        }
    }

    public static MyApplication getInstance() {return instance;}
}
