package by.onliner.news.services.likes;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    Call<LikesObjectListResponse> getLikes(@Path("project") String str1, @Path("post_id") String str2);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("/sdapi/news.api/{project}/comments/{comment_id}/like")
    Call<LikeCommentResponse> likeComment(@Path("project") String str1, @Path("comment_id") String str2);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @DELETE("/sdapi/news.api/{project}/comments/{comment_id}/like")
    Call<LikeCommentResponse> deslikeComment(@Path("project") String str1, @Path("comment_id") String str2);
}
