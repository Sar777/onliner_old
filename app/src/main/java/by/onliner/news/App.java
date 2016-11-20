package by.onliner.news;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;

import by.onliner.news.Database.DBCredentialsHelper;
import by.onliner.news.Database.DBUserHelper;
import by.onliner.news.Structures.Credentials.Credentials;
import by.onliner.news.Structures.User.User;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class App extends Application {
    private static Context mContext;
    private static AsyncHttpClient mClient = new AsyncHttpClient(true, 80, 443);

    private static Credentials mCredentials;
    private static User mLoggedUser;

    private static DBCredentialsHelper mDBCredentials;
    private static DBUserHelper mDBUserHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mClient.setResponseTimeout(5 * 1000);
        mClient.setConnectTimeout(5 * 1000);

        mClient.addHeader("Accept", "application/json, text/javascript, */*; q=0.01; charset=utf-8");
        mClient.addHeader("Content-Type", "application/json");

        mDBUserHelper = new DBUserHelper(this);
        mDBCredentials = new DBCredentialsHelper(this);

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
    public static DBCredentialsHelper getDBCredentials() {
        return mDBCredentials;
    }

    @NonNull
    public static DBUserHelper getDBUserHelper() {
        return mDBUserHelper;
    }

    public static Credentials getCredentials() {
        return mCredentials;
    }

    public static void setCredentials(Credentials credentials) {
        credentials.saveToDB();
        App.mCredentials = credentials;
    }

    public static User getLoggedUser() {
        return mLoggedUser;
    }

    public static void setLoggedUser(User user) {
        user.saveToDB();
        App.mLoggedUser = user;
    }
}