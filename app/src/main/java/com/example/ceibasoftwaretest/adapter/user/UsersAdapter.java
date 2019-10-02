package com.example.ceibasoftwaretest.adapter.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.database.data.User.Users;
import com.example.ceibasoftwaretest.ui.Post.PostActivity;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter  extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> implements Filterable {

    private List<Users> mUsersList = new ArrayList<>();
    private List<Users> mUsersListFull = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item,parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        Users users = mUsersList.get(position);
        if (users != null)
            holder.mUserName.setText(users.getName());
            holder.mUserEmail.setText(users.getEmail());
            holder.mUserCellPhone.setText(users.getPhone());


            holder.mShowPost.setOnClickListener(v ->{
                Intent intent = new Intent(mContext, PostActivity.class);
                intent.putExtra("idUser", users.getId());
                intent.putExtra("nameUser", users.getName());
                intent.putExtra("phoneUser", users.getPhone());
                intent.putExtra("emailUser", users.getEmail());
                mContext.startActivity(intent);
            });
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public void addUsersList(List<Users> users){
        this.mUsersList = users;
        this.mUsersListFull = new ArrayList<>(mUsersList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constrain) {
            List<Users> filteredList = new ArrayList<>();

            if (constrain == null || constrain.length() == 0){
                filteredList.addAll(mUsersListFull);
            }else{
                String filterPattern = constrain.toString().toLowerCase().trim();

                for (Users users: mUsersListFull){
                    if (users.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(users);
                    }else{
                        Toast.makeText(mContext, R.string.list_empty, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mUsersList.clear();
            mUsersList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView mUserName;
        TextView mUserCellPhone;
        TextView mUserEmail;
        Button mShowPost;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.nameTextView);
            mUserCellPhone = itemView.findViewById(R.id.cellPhoneTextView);
            mUserEmail = itemView.findViewById(R.id.emailTextView);
            mShowPost = itemView.findViewById(R.id.showPost);
        }
    }
}
