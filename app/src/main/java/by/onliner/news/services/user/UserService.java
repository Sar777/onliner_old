package by.onliner.news.services.user;

import by.onliner.news.structures.user.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by orion on 18.11.16.
 */

public interface UserService {
    String USER_API = "https://user.api.onliner.by";

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @GET("/user")
    Call<User> getUser(@Header("Authorization") String str);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @GET("/users/{user_id}")
    Call<User> getUser(@Header("Authorization") String str, @Path("user_id") String str2);
}
