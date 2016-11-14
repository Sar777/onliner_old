package by.onliner.news.Listeners;

import java.util.ArrayList;

import by.onliner.news.Structures.News.News;

/**
 * Created by orion on 23.10.16.
 */

public interface NewsListResponse {
    void onResult(boolean success, ArrayList<News> news);
}
