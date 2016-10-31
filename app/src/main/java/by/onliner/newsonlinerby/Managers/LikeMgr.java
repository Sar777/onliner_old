package by.onliner.newsonlinerby.Managers;

import android.util.Log;
import android.webkit.CookieManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Listeners.LikeCommentListener;
import by.onliner.newsonlinerby.Listeners.ResponseListener;
import by.onliner.newsonlinerby.Parser.JSON.JSONLikesParser;
import by.onliner.newsonlinerby.Structures.Comments.Like;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by Mi Air on 22.10.2016.
 */
public class LikeMgr {
    private static LikeMgr ourInstance = new LikeMgr();

    private LikeMgr() {
    }

    public static LikeMgr getInstance() {
        return ourInstance;
    }

    public void getAsyncLikes(String url, final ResponseListener<ArrayList<Like>> listener) {
        App.getAsyncHttpClient().get(App.getContext(), url, new RequestParams(), new AsyncHttpResponseHandler() {
            ResponseListener<ArrayList<Like>> asyncListener = listener;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                asyncListener.onResponse(new JSONLikesParser().parse(new String(responseBody)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                asyncListener.onResponse(new JSONLikesParser().parse(new String(responseBody)));
            }
        });
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

