package by.onliner.newsonlinerby.Managers;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Listeners.NewsListResponse;
import by.onliner.newsonlinerby.Parser.Parsers.NewsListParser;
import by.onliner.newsonlinerby.Structures.HeaderNews;
import cz.msebera.android.httpclient.Header;

/**
 * Created by orion on 23.10.16.
 */
public class NewsMgr {
    private static NewsMgr ourInstance = new NewsMgr();

    private AsyncHttpClient mClient = new AsyncHttpClient();

    public static NewsMgr getInstance() {
        return ourInstance;
    }

    private NewsMgr() {
        mClient.setTimeout(5 * 1000);
    }

    public void getAsyncNewsList(String url, RequestParams params, final NewsListResponse<ArrayList<HeaderNews>> listener) {
        mClient.get(url, params, new AsyncHttpResponseHandler() {
            NewsListResponse<ArrayList<HeaderNews>> asyncListener = listener;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                asyncListener.OnSuccess(new NewsListParser().parse(new String(responseBody))); ;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                asyncListener.onFailure();
            }
        });
    }
}
