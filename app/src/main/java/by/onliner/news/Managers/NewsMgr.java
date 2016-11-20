package by.onliner.news.Managers;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.onliner.news.App;
import by.onliner.news.Common.Common;
import by.onliner.news.Listeners.OnNewsListResponse;
import by.onliner.news.Parser.Parsers.NewsListParser;
import by.onliner.news.Structures.News.News;
import cz.msebera.android.httpclient.Header;

/**
 * Менеджер обработки и загрузки новостей
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
    public void getLoadingNewsList(final String projectId, boolean pull, final OnNewsListResponse listResponse) {
        RequestParams params = new RequestParams();
        if (pull)
            params.put("fromDate", getLastNews(projectId).getHeader().getPostDateUnix());
        else  // Если загрузка новойстей или обновление
            clearAllProjectNews(projectId);

        // сеть
        App.getAsyncHttpClient().get(Common.getUrlByProject(projectId), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listResponse.onResult(true, new NewsListParser(projectId).parse(new String(responseBody)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listResponse.onResult(false, null);
            }
        });
    }

    /**
     * Получение новости по url
     *
     * @param url Адрес новости
     * @return Содержимое новости в html
     */
    public String getNewsByUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        if (doc == null)
            return null;

        return doc.html();
    }

    /**
     * Получение списка новости по категории
     *
     * @param projectId the project id
     * @return the news list
     */
    public ArrayList<News> getNewsList(String projectId) {
        return mNews.get(projectId);
    }

    /**
     * Получение последней новости по по категории
     *
     * @param project Категория
     * @return Последняя новость
     */
    private News getLastNews(String project) {
        ArrayList<News> projectNews = mNews.get(project);
        if (projectNews.isEmpty())
            return null;

        for (int i = projectNews.size() - 1; i > 0; --i) {
            if (projectNews.get(i) != null)
                return projectNews.get(i);
        }

        return null;
    }

    /**
     * Очистка списка загруженных новостей по категории
     *
     * @param project Проект
     */
    private void clearAllProjectNews(String project) {
        ArrayList<News> projectNews = mNews.get(project);
        if (projectNews == null || projectNews.size() == 0)
            return;

        projectNews.clear();
    }

    /**
     * Очистка
     */
    public void clearAll() {
        mNews.clear();
    }
}
