package by.onliner.newsonlinerby.Listeners;

/**
 * Created by orion on 24.10.16.
 */

public interface LikeCommentListener {
    void OnSuccess(int code, String json);
    void onFailure(int code);
}
