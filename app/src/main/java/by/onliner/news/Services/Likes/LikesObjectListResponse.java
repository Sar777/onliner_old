package by.onliner.news.Services.Likes;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import by.onliner.news.Structures.Comments.Like;

/**
 * Created by orion on 18.11.16.
 */

public class LikesObjectListResponse {

    @SerializedName("comments")
    private HashMap<String, Like> mLikes;

    public HashMap<String, Like> getLikes() {
        return mLikes;
    }

    public void setLikes(HashMap<String, Like> likes) {
        this.mLikes = likes;
    }
}