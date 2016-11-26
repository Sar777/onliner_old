package by.onliner.news.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import by.onliner.news.Factory.News.NewsContentFactory;
import by.onliner.news.Managers.NewsMgr;
import by.onliner.news.Structures.News.News;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Created by orion on 15.11.16.
 */

public class AsyncNewsContentLoader extends AsyncTaskLoader<ArrayList<ViewObject>> {
    private String mUrl;
    private String mProject;
    private String mTitle;
    private News mNews;

    public AsyncNewsContentLoader(Context context, Bundle args) {
        super(context);
        mUrl = args.getString("URL");
        mProject = args.getString("PROJECT");
        mTitle = args.getString("TITLE");
    }

    @Override
    public ArrayList<ViewObject> loadInBackground() {
        String resp = NewsMgr.getInstance().getSyncNewsByUrl(mUrl);
        mNews = new News(mUrl, mProject, mTitle, resp);
        return NewsContentFactory.create(mNews);
    }

    public final News getNews() {
        return mNews;
    }
}
