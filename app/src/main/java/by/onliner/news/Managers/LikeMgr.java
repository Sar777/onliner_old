package by.onliner.news.Managers;

import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.util.HashMap;

import by.onliner.news.App;
import by.onliner.news.Listeners.LikeCommentListener;
import by.onliner.news.Services.Likes.LikeResponse;
import by.onliner.news.Services.Likes.LikeService;
import by.onliner.news.Services.ServiceFactory;
import by.onliner.news.Structures.Comments.Like;
import cz.msebera.android.httpclient.Header;
import retrofit2.Response;

/**
 * Менеджер обработки лайков
 */
public class LikeMgr {
    private static LikeMgr ourInstance = new LikeMgr();

    private LikeMgr() {
    }

    public static LikeMgr getInstance() {
        return ourInstance;
    }


    public HashMap<String, Like> getLikes(@NonNull final String project, @NonNull final String postId) {
        LikeService likeService = ServiceFactory.createRetrofitService(LikeService.class, String.format("https://%s.onliner.by", project));

        HashMap<String, Like> likes = null;
        try {
            Response<LikeResponse> response = likeService.getLikes(project, postId).execute();
            if (!response.isSuccessful())
                return null;

            likes = response.body().getLikes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return likes;
    }

    public void asyncLikeComment(String commentId, String project, final LikeCommentListener likeCommentListener) {
        App.getAsyncHttpClient().post(App.getContext(), getLikeUrl(commentId, project), new RequestParams(), new AsyncHttpResponseHandler() {
            LikeCommentListener listener =  likeCommentListener;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.OnResponse(statusCode, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.OnResponse(statusCode, new String(responseBody));
            }
        });
    }

    public String getLikeUrl(String commentId, String project) {
        return "https://" + project + ".onliner.by/sdapi/news.api/" + project + "/comments/" + commentId + "/like";
    }
}

