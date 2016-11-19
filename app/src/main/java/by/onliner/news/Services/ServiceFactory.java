package by.onliner.news.Services;

import by.onliner.news.App;
import by.onliner.news.Cookie.AddCookiesInterceptor;
import by.onliner.news.Cookie.ReceivedCookiesInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by orion on 18.11.16.
 */

public class ServiceFactory {
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        // Logs
        //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        //.addInterceptor(interceptor)
                        .addInterceptor(new AddCookiesInterceptor(App.getContext()))
                        .addInterceptor(new ReceivedCookiesInterceptor(App.getContext()))
                        .build())
                .build();

        T service = restAdapter.create(clazz);
        return service;
    }

    public static final String getAccessToken(String token) {
        return String.format("Bearer %s", token);
    }
}
