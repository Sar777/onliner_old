package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Created by Mi Air on 22.10.2016.
 */

public class Like implements Serializable {
    private Integer mCommentId;
    private Integer mCount;
    private Boolean mIsBest;

    public Like() {
        this.mCommentId = 0;
        this.mCount = 0;
        this.mIsBest = false;
    }

    public Like(Integer commentId, Integer mCount, Boolean best) {
        this.mCommentId = commentId;
        this.mCount = mCount;
        this.mIsBest = best;
    }

    public Integer getCommentId() {
        return mCommentId;
    }

    public void setCommentId(Integer commentId) {
        this.mCommentId = commentId;
    }

    public Integer getCount() {
        return mCount;
    }

    public void setCount(Integer count) {
        this.mCount = count;
    }

    public Boolean getBest() {
        return mIsBest;
    }

    public void setBest(Boolean best) {
        this.mIsBest = best;
    }
}
