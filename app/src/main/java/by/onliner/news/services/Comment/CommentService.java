package by.onliner.news.services.Comment;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by orion on 27.11.16.
 */

public interface CommentService {
    @Headers({
            "Accept: application/json, text/javascript, */*; q=0.01",
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8",
            "X-Requested-With: XMLHttpRequest"
    })
    @FormUrlEncoded
    @POST("comments/add")
    Call<CommentResponse> sendCommentMessage(@Field("message") String message, @Field("postId") Integer postId);
}
