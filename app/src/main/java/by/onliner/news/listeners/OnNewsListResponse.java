package by.onliner.news.listeners;

import java.util.ArrayList;

import by.onliner.news.structures.news.News;

/**
 * Created by orion on 23.10.16.
 */

public interface OnNewsListResponse {
    void onResult(ArrayList<News> news);
}
