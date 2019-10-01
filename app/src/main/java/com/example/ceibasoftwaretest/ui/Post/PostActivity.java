package com.example.ceibasoftwaretest.ui.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.database.data.Post.Post;
import com.example.ceibasoftwaretest.database.data.User.Users;
import com.example.ceibasoftwaretest.database.network.ApiClient;
import com.example.ceibasoftwaretest.ui.UsersList.UsersFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Call<List<Post>> call = ApiClient.apiInterface().getPostById("1");
        call.enqueue(new retrofit2.Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (response.isSuccessful()){

                }else{
                    Toast.makeText(getApplicationContext(), "An error occurred " + response.code(), Toast.LENGTH_LONG).show();
                    UsersFragment.hideCustomLoadingDialog();
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error occurred " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                UsersFragment.hideCustomLoadingDialog();
            }
        });

    }
}
