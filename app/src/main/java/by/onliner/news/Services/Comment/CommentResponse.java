package by.onliner.news.Services.Comment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by orion on 27.11.16.
 */

public class CommentResponse {
    @SerializedName("id")
    private String mId;

    @SerializedName("comment")
    private String mComment;

    public CommentResponse(String id, String comment) {
        this.mId = id;
        this.mComment = comment;
    }

    public String getId() {
        return mId;
    }

    public String getComment() {
        return mComment;
    }
}
