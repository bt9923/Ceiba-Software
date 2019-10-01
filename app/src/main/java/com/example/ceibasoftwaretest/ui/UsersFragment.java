package com.example.ceibasoftwaretest.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.adapter.UsersAdapter;
import com.example.ceibasoftwaretest.database.data.Users;
import com.example.ceibasoftwaretest.database.network.ApiClient;
import com.example.ceibasoftwaretest.viewmodel.UsersFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    //<editor-fold desc="Vars">

    UsersAdapter mUsersAdapers;

    //</editor-fold>

    //<editor-fold desc="ButterKnife">

    @BindView(R.id.usersRecyclerView)
    RecyclerView mUsersRecyclerView;

    //</editor-fold>

    //<editor-fold desc="LifeCycle">


    public UsersFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);

        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUsersRecyclerView.setHasFixedSize(true);

        mUsersAdapers = new UsersAdapter(new ArrayList<Users>());
//        mUsersRecyclerView.setAdapter(mUsersAdapers);

        UsersFragmentViewModel mModel = ViewModelProviders.of(this)
                .get(UsersFragmentViewModel.class);

        mModel.getAllUsers().observe(this, users -> {
            mUsersAdapers.addUsersList(users);
        });

        Call<List<Users>> call = ApiClient.apiInterface().getUsers();

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()){
                    mUsersAdapers = new UsersAdapter(response.body());
                    mUsersRecyclerView.setAdapter(mUsersAdapers);

                }else{
                    Toast.makeText(getActivity(), "An error ocurred " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getActivity(), "An error ocurred " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    //</editor-fold>
}
