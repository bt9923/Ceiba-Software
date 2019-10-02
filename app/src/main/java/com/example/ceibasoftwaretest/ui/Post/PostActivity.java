package com.example.ceibasoftwaretest.ui.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.adapter.post.PostAdapter;
import com.example.ceibasoftwaretest.database.AppDatabase;
import com.example.ceibasoftwaretest.database.data.Post.Post;
import com.example.ceibasoftwaretest.database.data.Post.PostDao;
import com.example.ceibasoftwaretest.database.data.User.Users;
import com.example.ceibasoftwaretest.database.network.ApiClient;
import com.example.ceibasoftwaretest.ui.UsersList.UsersFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    //<editor-fold desc="ButterKnife">

    PostAdapter postAdapter;
    Bundle extras;

    //</editor-fold>

    //<editor-fold desc="ButterKnife">

    @BindView(R.id.nameTextView)
    TextView mNameTextView;

    @BindView(R.id.cellPhoneTextView)
    TextView mPhoneTextView;

    @BindView(R.id.emailTextView)
    TextView mEmailTextView;

    @BindView(R.id.postRecyclerView)
    RecyclerView mPostRecyclerView;

    //</editor-fold>


    //<editor-fold desc="LifeCycle">

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        initView();

        assert extras != null;
        Call<List<Post>> call = ApiClient.apiInterface().getPostById(extras.getInt("idUser"));
        call.enqueue(new retrofit2.Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (response.isSuccessful()){
                    List<Post> post = response.body();

                    postAdapter = new PostAdapter(getApplicationContext(), post);
                    mPostRecyclerView.setAdapter(postAdapter);
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

    private void initView() {

        extras = getIntent().getExtras();

        if (extras != null)
            mNameTextView.setText(extras.getString("nameUser"));
            mPhoneTextView.setText(extras.getString("phoneUser"));
            mEmailTextView.setText(extras.getString("emailUser"));


        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    //</editor-fold>

}
