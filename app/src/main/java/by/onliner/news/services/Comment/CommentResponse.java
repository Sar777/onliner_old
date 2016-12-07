package by.onliner.news.services.Comment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by orion on 27.11.16.
 */

public class CommentResponse {
    @SerializedName("id")
    private String mId;

    @SerializedName("comment")
    private String mComment;

    @SerializedName("error")
    private String mError;

    public CommentResponse(String id, String comment, String error) {
        this.mId = id;
        this.mComment = comment;
        this.mError = error;
    }

    public String getId() {
        return mId;
    }

    public String getComment() {
        return mComment;
    }

    public String getError() {
        return mError;
    }
}
