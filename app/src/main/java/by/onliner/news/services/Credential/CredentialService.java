package by.onliner.news.services.Credential;

import by.onliner.news.structures.credentials.Credentials;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by orion on 18.11.16.
 */

public interface CredentialService {
    String CREDENTIALS_API = "https://credentials.api.onliner.by";

    String GRANT_TYPE = "password";
    String CLIENT_ID = "onliner_user";

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<Credentials> getPasswordCredentials(@Field("username") String str, @Field("password") String str2, @Field("client_id") String str3, @Field("grant_type") String str4);
}
