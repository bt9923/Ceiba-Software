package com.example.ceibasoftwaretest.ui.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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

    //<editor-fold desc="Vars">

    PostAdapter postAdapter;
    Bundle extras;
    LoaderSearchPosts loaderSearchInsurances = new LoaderSearchPosts();

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

    @BindView(R.id.unloadLayout)
    LinearLayout mUnloadLayout;

    //</editor-fold>

    //<editor-fold desc="LifeCycle">

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        initView();
    }

    //</editor-fold>

    //<editor-fold desc="Init Vars">
    private void initView() {

        mUnloadLayout.setVisibility(View.VISIBLE);
        loaderSearchInsurances = new LoaderSearchPosts();
        loaderSearchInsurances.execute();

        extras = getIntent().getExtras();

        if (extras != null)
            mNameTextView.setText(extras.getString("nameUser"));
            mPhoneTextView.setText(extras.getString("phoneUser"));
            mEmailTextView.setText(extras.getString("emailUser"));

        mPostRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    //</editor-fold>

    //<editor-fold desc="Async Task">
    private class LoaderSearchPosts extends AsyncTask<Void, String ,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Animation anim = AnimationUtils.loadAnimation(PostActivity.this, R.anim.fadein);
            anim.setDuration(500);
            mUnloadLayout.startAnimation(anim);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            assert extras != null;
            Call<List<Post>> call = ApiClient.apiInterface().getPostById(extras.getInt("idUser"));
            call.enqueue(new retrofit2.Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                    if (response.isSuccessful()){
                        List<Post> post = response.body();

                        postAdapter = new PostAdapter(getApplicationContext(), post);
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
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){

                Animation anim = AnimationUtils.loadAnimation(PostActivity.this, R.anim.fadeout);
                anim.setDuration(1500);
                if (mUnloadLayout != null) {
                    mUnloadLayout.startAnimation(anim);
                }

                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mUnloadLayout.setVisibility(View.GONE);
                        showData();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }

    }

    private void showData() {
        mPostRecyclerView.setAdapter(postAdapter);
    }

    //</editor-fold>

}
