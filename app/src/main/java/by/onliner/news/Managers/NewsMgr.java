package by.onliner.news.Managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.onliner.news.Common.Common;
import by.onliner.news.Constants.Constant;
import by.onliner.news.Listeners.OnNewsListResponse;
import by.onliner.news.Parser.Parsers.NewsListParser;
import by.onliner.news.Services.News.NewsService;
import by.onliner.news.Services.ServiceFactory;
import by.onliner.news.Structures.News.News;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
     * @param project      название проекта
     * @param pull         подгрузка
     * @param listener     обработка
     */
    public void getLoadingNewsList(final String project, final boolean pull, boolean refresh, final OnNewsListResponse listener) {
        ArrayList<News> news = getNewsList(project);
        if (!pull && !refresh && !news.isEmpty()) {
            listener.onResult(news);
            return;
        }

        Map<String, String> params = new HashMap<>();
        if (pull)
            params.put("fromDate", getLastNews(project).getPreview().getPostDateUnix().toString());

        // сеть
        final NewsService service = ServiceFactory.createRetrofitService(NewsService.class, Constant.mBaseURL);
        service.getNewsList(Common.getUrlByProject(project), params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful())
                    listener.onResult(null);
                else {
                    try {
                        ArrayList<News> objects = new NewsListParser(project).parse(response.body().string());
                        if (pull)
                            getNewsList(project).remove(getNewsList(project).size() - 1);
                        else
                            clearProjectNews(project);

                        addNews(project, objects);
                        listener.onResult(objects);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                listener.onResult(null);
            }
        });
    }

    /**
     * Получение новости по url
     *
     * @param url Адрес новости
     * @return Содержимое новости в html
     */
    public String getSyncNewsByUrl(String url) {
        NewsService service = ServiceFactory.createRetrofitService(NewsService.class, Constant.mBaseURL);
        String result = null;
        try {
            Response<ResponseBody> response = service.getNews(url).execute();
            if (response.isSuccessful())
                result = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Получение списка новости по категории
     *
     * @param project the project id
     * @return the news list
     */
    public ArrayList<News> getNewsList(String project) {
        if (!mNews.containsKey(project))
            throw new IllegalArgumentException("Unknown project: " + project);

        return mNews.get(project);
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
    private void clearProjectNews(String project) {
        if (!mNews.containsKey(project))
            throw new IllegalArgumentException("Unknown project: " + project);

        mNews.get(project).clear();
    }

    /**
     * Добавление группы новостей
     * @param project Проект
     * @param newsList Список новостей
     */
    private void addNews(String project, ArrayList<News> newsList) {
        if (!mNews.containsKey(project))
            throw new IllegalArgumentException("Unknown project: " + project);

        mNews.get(project).addAll(newsList);
    }

    /**
     * Очистка
     */
    public void clearAll() {
        mNews.clear();
    }
}
