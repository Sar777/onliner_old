package by.onliner.newsonlinerby.Managers;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Listeners.NewsListResponse;
import by.onliner.newsonlinerby.Parser.Parsers.NewsListParser;
import by.onliner.newsonlinerby.Structures.News.News;
import cz.msebera.android.httpclient.Header;

/**
 * Created by orion on 23.10.16.
 */
public class NewsMgr {
    private static NewsMgr ourInstance = new NewsMgr();

    public static NewsMgr getInstance() {
        return ourInstance;
    }

    private Map<String/*project*/, ArrayList<News>> mNews;

    public NewsMgr() {
        mNews = new HashMap<>();
        mNews.put("auto", new ArrayList<News>());
        mNews.put("tech", new ArrayList<News>());
        mNews.put("realt", new ArrayList<News>());
        mNews.put("people", new ArrayList<News>());
    }

    /**
     * Получение списка новостей
     *
     * @param projectId    название проекта
     * @param pull         подгрузка
     * @param listResponse обработка
     */

    public void getLoadingNewsList(final String projectId, boolean pull, final NewsListResponse listResponse) {
        RequestParams params = new RequestParams();
        if (pull)
            params.put("fromDate", getLastNews(projectId).getHeader().getPostDateUnix());
        else  // Если загрузка новойстей или обновление
            clearAllProjectNews(projectId);

        // сеть
        App.getAsyncHttpClient().get(getUrl(projectId), params, new AsyncHttpResponseHandler() {
            NewsListResponse listener = listResponse;
            String project = projectId;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mNews.get(project).addAll(new NewsListParser(getUrl(projectId)).parse(new String(responseBody)));
                listener.onResult(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onResult(false);
            }
        });
    }

    public ArrayList<News> getNewsList(String projectId) {
        return mNews.get(projectId);
    }

    public static String getUrl(String project) {
        return "https://" + project + ".onliner.by";
    }

    public News getLastNews(String project) {
        ArrayList<News> projectNews = mNews.get(project);
        if (projectNews == null || projectNews.size() == 0)
            return null;

        return projectNews.get(projectNews.size() - 1);
    }

    private void clearAllProjectNews(String project) {
        ArrayList<News> projectNews = mNews.get(project);
        if (projectNews == null || projectNews.size() == 0)
            return;

        projectNews.clear();;
    }

    public void clearAll() {
        mNews.clear();
    }
}
