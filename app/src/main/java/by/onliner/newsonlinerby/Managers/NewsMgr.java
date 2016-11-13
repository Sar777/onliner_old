package by.onliner.newsonlinerby.Managers;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Listeners.NewsListResponse;
import by.onliner.newsonlinerby.Listeners.ViewNewsListener;
import by.onliner.newsonlinerby.Parser.Parsers.NewsListParser;
import by.onliner.newsonlinerby.Structures.News.News;
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
                listener.onResult(true, new NewsListParser(getUrl(projectId)).parse(new String(responseBody)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onResult(false, null);
            }
        });
    }

    /**
     * Получение новости по url
     *
     * @param url              Адрес новости
     * @param viewNewsListener Обработчик загруженных данных
     */
    public void getNewsByUrl(String url, final ViewNewsListener viewNewsListener) {
        App.getAsyncHttpClient().get(url, null, new AsyncHttpResponseHandler() {
            ViewNewsListener listener = viewNewsListener;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onResponse(statusCode, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onResponse(statusCode, new String(responseBody));
            }
        });
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
     * Формирование адреса новости по категории
     *
     * @param project Категория
     * @return Сформировання строку
     */
    private static String getUrl(String project) {
        return "https://" + project + ".onliner.by";
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
