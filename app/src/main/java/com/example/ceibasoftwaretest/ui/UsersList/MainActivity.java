package com.example.ceibasoftwaretest.ui.UsersList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.ui.UsersList.UsersFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, new UsersFragment())
                    .addToBackStack("USERFRAGMENT")
                    .commit();
        }
    }

}
