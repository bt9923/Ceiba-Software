package com.example.ceibasoftwaretest.database.data.Post;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM post")
    LiveData<List<Post>> loadAllPosts();

    @Insert
    void insertAll(Post... posts);

}
