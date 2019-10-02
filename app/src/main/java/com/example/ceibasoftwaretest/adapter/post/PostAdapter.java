package com.example.ceibasoftwaretest.adapter.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceibasoftwaretest.R;
import com.example.ceibasoftwaretest.database.data.Post.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private Context mContext;
    private List<Post> mPostsList;

    public PostAdapter(Context mContext, List<Post> mPostsList) {
        this.mContext = mContext;
        this.mPostsList = mPostsList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_item, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = mPostsList.get(position);
        holder.mTvTitlePost.setText(post.getTitle());
        holder.mTvDescriptionPost.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return mPostsList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvTitlePost;
        private TextView mTvDescriptionPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvTitlePost = itemView.findViewById(R.id.titlePost);
            mTvDescriptionPost = itemView.findViewById(R.id.descriptionPost);
        }
    }
}
