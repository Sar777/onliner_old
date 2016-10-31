package by.onliner.newsonlinerby;

import android.app.Application;
import android.content.Context;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class App extends Application {
    private static Context mContext;
    private static AsyncHttpClient mClient = new AsyncHttpClient(true, 80, 443);
    private static PersistentCookieStore mCookieStore;

    private static WebView webView;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mClient.setResponseTimeout(5 * 1000);
        mClient.setConnectTimeout(5 * 1000);

        mClient.addHeader("Accept", "application/json, text/javascript, */*; q=0.01; charset=utf-8");
        mClient.addHeader("Content-Type", "application/json");

        mCookieStore = new PersistentCookieStore(this);
        mClient.setCookieStore(mCookieStore);
    }

    public static Context getContext(){
        return mContext;
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        return mClient;
    }

    public static PersistentCookieStore getCookieStore() {
        return mCookieStore;
    }

    public static WebView getWebView() {
        return webView;
    }

    public static void setWebView(WebView webView1) {
        webView = webView1;
    }
}