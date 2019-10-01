package com.example.ceibasoftwaretest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ceibasoftwaretest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, new UsersFragment())
                    .addToBackStack("USERGRAGMENT")
                    .commit();
        }
    }
}
