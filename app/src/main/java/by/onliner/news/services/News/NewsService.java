package by.onliner.news.services.News;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by orion on 18.11.16.
 */

public interface NewsService {
    @GET
    Call<ResponseBody> getNewsList(@Url String url, @QueryMap Map<String, String> options);

    @GET
    Call<ResponseBody> getNews(@Url String url);
}
