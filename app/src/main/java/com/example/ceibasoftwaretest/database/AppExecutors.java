package com.example.ceibasoftwaretest.database;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    //For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sIntance;
    private static Executor diskIO;
    private static Executor mainThread;
    private static Executor networkIO;

    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static AppExecutors getInstance(){
        if (sIntance == null){
            synchronized (LOCK){
                sIntance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sIntance;
    }


    public Executor diskIo(){return diskIO;}

    public Executor mainThread(){return mainThread;};

    public Executor networkIO(){return networkIO;}

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new android.os.Handler(Looper.getMainLooper());


        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
