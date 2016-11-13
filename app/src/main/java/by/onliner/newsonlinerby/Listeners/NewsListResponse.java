package by.onliner.newsonlinerby.Listeners;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Structures.News.News;

/**
 * Created by orion on 23.10.16.
 */

public interface NewsListResponse {
    void onResult(boolean success, ArrayList<News> news);
}
