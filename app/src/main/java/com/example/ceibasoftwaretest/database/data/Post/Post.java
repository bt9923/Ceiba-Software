package com.example.ceibasoftwaretest.database.data.Post;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "titlePost")
    private String titlePost;

    @ColumnInfo(name = "descriptionPost")
    private String descriptionPost;


    public Post() {
    }

    public Post(int id, String titlePost, String descriptionPost) {
        this.id = id;
        this.titlePost = titlePost;
        this.descriptionPost = descriptionPost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public void setTitlePost(String titlePost) {
        this.titlePost = titlePost;
    }

    public String getDescriptionPost() {
        return descriptionPost;
    }

    public void setDescriptionPost(String descriptionPost) {
        this.descriptionPost = descriptionPost;
    }
}
