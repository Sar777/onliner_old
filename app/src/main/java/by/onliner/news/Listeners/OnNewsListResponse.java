package by.onliner.news.Listeners;

import java.util.ArrayList;

import by.onliner.news.Structures.News.News;

/**
 * Created by orion on 23.10.16.
 */

public interface OnNewsListResponse {
    void onResult(boolean cache, ArrayList<News> news);
}
