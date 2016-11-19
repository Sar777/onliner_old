package by.onliner.news.Structures.Credentials;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import by.onliner.news.Listeners.Credentials.OnCredentialsRefreshListener;
import by.onliner.news.Services.Credential.CredentialService;
import by.onliner.news.Services.ServiceFactory;
import retrofit2.Response;

/**
 * Токены авторизации
 */
public class Credentials {
    @SerializedName("access_token")
    private final String mAccessToken;
    @SerializedName("refresh_token")
    private final String mRefreshToken;

    private String mDateStore;

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

    @NonNull
    public String getDateStore() {
        return mDateStore;
    }

    public void setDateStore(String date) {
        mDateStore = date;
    }

    public static void RefreshCredintialsIfNeed(final String username, final String password, final OnCredentialsRefreshListener listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final CredentialService service = ServiceFactory.createRetrofitService(CredentialService.class, CredentialService.CREDENTIALS_API);
                try {
                    Response<Credentials> response = service.getPasswordCredentials(username, password, CredentialService.CLIENT_ID, CredentialService.GRANT_TYPE).execute();
                    listener.onRefresh(response.body());
                } catch (IOException e) {
                    listener.onRefresh(null);
                }
            }
        });
    }
}