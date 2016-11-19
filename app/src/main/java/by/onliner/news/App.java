package by.onliner.news;

import android.app.Application;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

import by.onliner.news.Structures.Credentials.Credentials;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class App extends Application {
    private static Context mContext;
    private static AsyncHttpClient mClient = new AsyncHttpClient(true, 80, 443);

    private static Credentials mCredentials;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mClient.setResponseTimeout(5 * 1000);
        mClient.setConnectTimeout(5 * 1000);

        mClient.addHeader("Accept", "application/json, text/javascript, */*; q=0.01; charset=utf-8");
        mClient.addHeader("Content-Type", "application/json");
    }

    public static Context getContext(){
        return mContext;
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        return mClient;
    }

    public static Credentials getCredentials() {
        return mCredentials;
    }

    public static void setCredentials(Credentials credentials) {
        App.mCredentials = credentials;
    }
}