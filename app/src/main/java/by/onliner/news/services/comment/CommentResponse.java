package by.onliner.news.services.comment;

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

    @SerializedName("redirect")
    private String mRedirect;

    public CommentResponse(String id, String comment, String error, String redirect) {
        this.mId = id;
        this.mComment = comment;
        this.mError = error;
        this.mRedirect = redirect;
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

    public String getRedirect() {
        return mRedirect;
    }
}
