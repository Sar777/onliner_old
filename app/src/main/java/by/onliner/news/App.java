package by.onliner.news;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.loopj.android.http.AsyncHttpClient;

import by.onliner.news.Database.DBHelper;
import by.onliner.news.Structures.Credentials.Credentials;
import by.onliner.news.Structures.User.User;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class App extends Application {
    private static Context mContext;
    private static AsyncHttpClient mClient = new AsyncHttpClient(true, 80, 443);

    // Cookie
    private static ClearableCookieJar mClearableCookieJar;

    private static Credentials mCredentials;
    private static User mLoggedUser;

    private static DBHelper mDBHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mClient.setResponseTimeout(5 * 1000);
        mClient.setConnectTimeout(5 * 1000);

        mClient.addHeader("Accept", "application/json, text/javascript, */*; q=0.01; charset=utf-8");
        mClient.addHeader("Content-Type", "application/json");

        mClearableCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));

        mDBHelper = new DBHelper(this);

        mCredentials = Credentials.create();
        mLoggedUser = User.create();
    }

    @NonNull
    public static Context getContext(){
        return mContext;
    }

    @NonNull
    public static AsyncHttpClient getAsyncHttpClient() {
        return mClient;
    }

    @NonNull
    public static ClearableCookieJar getClearableCookieJar() {
        return mClearableCookieJar;
    }

    @NonNull
    public static DBHelper getDBHelper() {
        return mDBHelper;
    }

    public static Credentials getCredentials() {
        return mCredentials;
    }

    public static void setCredentials(Credentials credentials) {
        credentials.saveToDB();
        mCredentials = credentials;
    }

    public static User getLoggedUser() {
        return mLoggedUser;
    }

    public static void setLoggedUser(User user) {
        user.saveToDB();
        mLoggedUser = user;
    }

    public static void logoutUser() {
        if (mLoggedUser != null)
            mLoggedUser.delete();

        if (mCredentials != null)
            mCredentials.delete();

        mLoggedUser = null;
        mCredentials = null;

        mClearableCookieJar.clear();
    }
}