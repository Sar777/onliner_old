package by.onliner.news.Services.Likes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by orion on 19.11.16.
 */

public class LikeCommentResponse {
    @SerializedName("likes")
    private String mLikes;

    @SerializedName("errors")
    private String mError;

    @SerializedName("user")
    public String[] mUser;

    public LikeCommentResponse(String likes, String error, String[] user) {
        this.mLikes = likes;
        this.mError = error;
        this.mUser = user;
    }

    public String getLikes() {
        return mLikes;
    }

    public String getError() {
        return mError;
    }

    public String[] getUser() {
        return mUser;
    }
}
