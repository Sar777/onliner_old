package by.onliner.news.Services.Auth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by orion on 19.11.16.
 */

public interface AuthService {
    String AUTH_API = "https://user.api.onliner.by";

    @GET("/login")
    Call<ResponseBody> getHttpAccountToken();

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("/login")
    Call<ResponseBody> loginAccount(@Body AuthHttpRequest object);

    @GET
    Call<ResponseBody> isLogged(@Url String url);
}
