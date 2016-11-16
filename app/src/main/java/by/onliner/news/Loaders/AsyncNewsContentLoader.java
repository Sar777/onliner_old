package by.onliner.news.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import by.onliner.news.Builders.News.NewsContentFactory;
import by.onliner.news.Managers.NewsMgr;
import by.onliner.news.Parser.Parsers.BodyNewsParser;
import by.onliner.news.Structures.News.News;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Created by orion on 15.11.16.
 */

public class AsyncNewsContentLoader extends AsyncTaskLoader<ArrayList<ViewObject>> {
    private String mUrl;

    public AsyncNewsContentLoader(Context context, Bundle args) {
        super(context);
        mUrl = args.getString("URL");
    }

    @Override
    public ArrayList<ViewObject> loadInBackground() {
        String content = NewsMgr.getInstance().getNewsByUrl(mUrl);
        if (content.isEmpty())
            return null;

        News news = new BodyNewsParser().parse(content);
        if (news == null)
            return null;

        return NewsContentFactory.create(news);
    }
}
