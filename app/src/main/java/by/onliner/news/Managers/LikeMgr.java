package by.onliner.news.Managers;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import by.onliner.news.Listeners.OnLikeCommentListener;
import by.onliner.news.Services.Likes.LikeCommentResponse;
import by.onliner.news.Services.Likes.LikeService;
import by.onliner.news.Services.Likes.LikesObjectListResponse;
import by.onliner.news.Services.ServiceFactory;
import by.onliner.news.Structures.Comments.Like;
import cz.msebera.android.httpclient.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
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
        LikeService service = ServiceFactory.createRetrofitService(LikeService.class, String.format("https://%s.onliner.by", project));

        HashMap<String, Like> likes = null;
        try {
            Response<LikesObjectListResponse> response = service.getLikes(project, postId).execute();
            if (!response.isSuccessful())
                return null;

            likes = response.body().getLikes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return likes;
    }

    public void likeComment(@NonNull String comment_id, @NonNull String project, final OnLikeCommentListener listener) {
        LikeService service = ServiceFactory.createRetrofitService(LikeService.class, String.format("https://%s.onliner.by", project));

        service.likeComment(project, comment_id).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                LikeCommentResponse likeCommentResponse = null;
                try {
                    if (!response.isSuccessful())
                        likeCommentResponse = new Gson().fromJson(response.errorBody().string(), LikeCommentResponse.class);
                    else
                        likeCommentResponse = response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listener.OnResponse(response.raw().code(), likeCommentResponse);
            }

            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                t.printStackTrace();
                listener.OnResponse(HttpStatus.SC_BAD_REQUEST, null);
            }
        });
    }

    public void deslikeComment(@NonNull String comment_id, @NonNull String project, final OnLikeCommentListener listener) {
        LikeService service = ServiceFactory.createRetrofitService(LikeService.class, String.format("https://%s.onliner.by", project));

        service.deslikeComment(project, comment_id).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                LikeCommentResponse likeCommentResponse = null;
                try {
                    if (!response.isSuccessful())
                        likeCommentResponse = new Gson().fromJson(response.errorBody().string(), LikeCommentResponse.class);
                    else
                        likeCommentResponse = response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listener.OnResponse(response.raw().code(), likeCommentResponse);
            }

            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                t.printStackTrace();
                listener.OnResponse(HttpStatus.SC_BAD_REQUEST, null);
            }
        });
    }
}

