package by.onliner.news.Structures.Credentials;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import by.onliner.news.Services.Credential.CredentialService;
import by.onliner.news.Services.ServiceFactory;

/**
 * Токены авторизации
 */
public class Credentials {
    @SerializedName("access_token")
    private final String mAccessToken;
    @SerializedName("refresh_token")
    private final String mRefreshToken;

    public Credentials(@NonNull String accessToken, @NonNull String refreshToken) {
        this.mAccessToken = accessToken;
        this.mRefreshToken = refreshToken;
    }

    @NonNull
    public String getAccessToken() {
        return this.mAccessToken;
    }

    @NonNull
    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    public static class Network {
        public static String getAccessToken(String username, String password) {
            CredentialService credentialService = ServiceFactory.createRetrofitService(CredentialService.class, CredentialService.CREDENTIALS_API);
            try {
                Credentials credentials = credentialService.getPasswordCredentials(username, password, CredentialService.CLIENT_ID, CredentialService.GRANT_TYPE).execute().body();
                if (credentials == null)
                    return null;

                return credentials.getAccessToken();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}