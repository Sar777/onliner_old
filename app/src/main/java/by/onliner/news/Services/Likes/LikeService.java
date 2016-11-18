package by.onliner.news.Services.Likes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by orion on 18.11.16.
 */

public interface LikeService {
    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @GET("/sdapi/news.api/{project}/posts/{post_id}/likes")
    Call<LikeResponse> getLikes(@Path("project") String str1, @Path("post_id") String str2);
}
