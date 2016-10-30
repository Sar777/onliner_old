package by.onliner.newsonlinerby;

import android.app.Application;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class App extends Application {
    private static Context mContext;
    private static AsyncHttpClient mClient = new AsyncHttpClient();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mClient.setResponseTimeout(5 * 1000);
        mClient.setConnectTimeout(5 * 1000);
    }

    public static Context getContext(){
        return mContext;
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        return mClient;
    }
}