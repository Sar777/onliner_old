package by.onliner.newsonlinerby;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}