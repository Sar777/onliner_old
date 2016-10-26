package by.onliner.newsonlinerby.Managers;

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

/**
 * Created by Mi Air on 22.10.2016.
 */
public class LikeMgr {
    private static String ACCEPT_TYPE = "application/json";

    private static LikeMgr ourInstance = new LikeMgr();
    private AsyncHttpClient mClient = new AsyncHttpClient(true, 80, 443);

    private LikeMgr() {
        mClient.addHeader("Accept", ACCEPT_TYPE);
    }

    public static LikeMgr getInstance() {
        return ourInstance;
    }

    public void getAsyncLikes(String url, final ResponseListener<ArrayList<Like>> listener) {
        mClient.get(App.getContext(), url, new RequestParams(), new AsyncHttpResponseHandler() {
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

    public void asyncLikeComment(Integer commentId, String project, final LikeCommentListener likeCommentListener) {
        String url = "https://" + project + ".onliner.by/sdapi/news.api/" + project + "/comments/" + commentId + "/like";
        mClient.get(App.getContext(), url, new RequestParams(), new AsyncHttpResponseHandler() {
            LikeCommentListener listener =  likeCommentListener;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.OnSuccess(statusCode, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure(statusCode);
            }
        });
    }
}

