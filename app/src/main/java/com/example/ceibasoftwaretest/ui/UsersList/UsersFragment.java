package com.example.ceibasoftwaretest.ui.UsersList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.adapter.user.UsersAdapter;
import com.example.ceibasoftwaretest.ui.ViewDialog;
import com.example.ceibasoftwaretest.viewmodel.UsersFragmentViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersFragment extends Fragment {

    //<editor-fold desc="Vars">

    UsersAdapter mUsersAdapters;
    UsersFragmentViewModel usersFragmentViewModel;
    static ViewDialog viewDialog;

    //</editor-fold>

    //<editor-fold desc="ButterKnife">

    @BindView(R.id.emptyListTextView)
    TextView mTvEMptyList;

    @BindView(R.id.usersRecyclerView)
    RecyclerView mUsersRecyclerView;

    //</editor-fold>

    //<editor-fold desc="LifeCycle">

    public UsersFragment() {
        //Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);

        viewDialog = new ViewDialog(getActivity());

        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUsersRecyclerView.setHasFixedSize(true);

        mUsersAdapters = new UsersAdapter();
        mUsersRecyclerView.setAdapter(mUsersAdapters);

        UsersFragment.showCustomLoadingDialog();

        usersFragmentViewModel = ViewModelProviders.of(this).get(UsersFragmentViewModel.class);
        usersFragmentViewModel.getAllUsers().observe(this, users -> {
            mUsersAdapters.addUsersList(users);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UsersFragment.hideCustomLoadingDialog();
                }
            },1000);
        });

        return view;
    }


    public static void showCustomLoadingDialog() {
        //..show gif
        viewDialog.showDialog();
    }

    public static void hideCustomLoadingDialog() {
        viewDialog.hideDialog();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) item.getActionView();

        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mUsersAdapters.getFilter().filter(newText);

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    //</editor-fold>
}
