package com.example.ceibasoftwaretest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.database.data.Users;

import java.util.List;

public class UsersAdapter  extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder>{

    private List<Users> mUSersList;

    public UsersAdapter(List<Users> mUSersList) {
        this.mUSersList = mUSersList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item,parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        Users users = mUSersList.get(position);
        if (users != null)
            holder.mUserName.setText(users.getName());
            holder.mUserEmail.setText(users.getEmail());
            holder.mUserCellPhone.setText(users.getPhone());
    }

    @Override
    public int getItemCount() {
        return mUSersList.size();
    }

    public void addUsersList(List<Users> users){
        mUSersList = users;
        notifyDataSetChanged();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView mUserName;
        TextView mUserCellPhone;
        TextView mUserEmail;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.nameTextView);
            mUserCellPhone = itemView.findViewById(R.id.cellPhoneTextView);
            mUserEmail = itemView.findViewById(R.id.emailTextView);
        }
    }
}
