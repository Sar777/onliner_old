package by.onliner.news.listeners;

import by.onliner.news.services.likes.LikeCommentResponse;

/**
 * Created by orion on 24.10.16.
 */

public interface OnLikeCommentListener {
    void OnResponse(int errCode, LikeCommentResponse response);
}
