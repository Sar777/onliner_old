package by.onliner.news.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by orion on 18.11.16.
 */

public class ServiceFactory {
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = restAdapter.create(clazz);
        return service;
    }

    public static final String getAccessToken(String token) {
        return String.format("Bearer %s", token);
    }
}
