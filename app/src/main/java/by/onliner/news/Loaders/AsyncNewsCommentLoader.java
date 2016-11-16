package by.onliner.news.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Created by orion on 15.11.16.
 */

public class AsyncNewsCommentLoader extends AsyncTaskLoader<ArrayList<ViewObject>> {
    private String mUrl;

    public AsyncNewsCommentLoader(Context context, Bundle args) {
        super(context);
        mUrl = args.getString("URL");
    }

    @Override
    public ArrayList<ViewObject> loadInBackground() {
        return null;
    }
}
