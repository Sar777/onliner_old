package by.onliner.news.Listeners;

import by.onliner.news.Services.Likes.LikeCommentResponse;

/**
 * Created by orion on 24.10.16.
 */

public interface OnLikeCommentListener {
    void OnResponse(int errCode, LikeCommentResponse response);
}
