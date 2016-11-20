package by.onliner.news.Services.Likes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by orion on 19.11.16.
 */

public class LikeCommentResponse {
    @SerializedName("likes")
    private String mLikes;

    @SerializedName("errors")
    private String mErrors;

    public LikeCommentResponse(String likes, String errors) {
        this.mLikes = likes;
        this.mErrors = errors;
    }

    public String getLikes() {
        return mLikes;
    }

    public String getErrors() {
        return mErrors;
    }
}
