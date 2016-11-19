package by.onliner.news.Managers;

import android.os.Handler;

import java.io.IOException;

import by.onliner.news.Constants.Constant;
import by.onliner.news.Listeners.Auth.OnLoginCheckerListener;
import by.onliner.news.Listeners.Auth.OnLoginCompleteListener;
import by.onliner.news.Listeners.Auth.OnLoginValidateAccount;
import by.onliner.news.Services.Auth.AuthHttpRequest;
import by.onliner.news.Services.Auth.AuthService;
import by.onliner.news.Services.Credential.CredentialService;
import by.onliner.news.Services.ServiceFactory;
import by.onliner.news.Structures.Credentials.Credentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by orion on 24.10.16.
 */
public class AuthMgr {
    private static AuthMgr ourInstance = new AuthMgr();

    private AuthMgr() {
    }

    public static AuthMgr getInstance() {
        return ourInstance;
    }

    public void loginAccount(final String username, final String password, final OnLoginCompleteListener listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final AuthService service = ServiceFactory.createRetrofitService(AuthService.class, AuthService.AUTH_API);
                try {
                    // Получение токена
                    Response<ResponseBody> respToken = service.getHttpAccountToken().execute();
                    if (!respToken.isSuccessful()) {
                        listener.onLoginStatus(false);
                        return;
                    }

                    // Авторизавия токена
                    Response<ResponseBody> respAuth = service.loginAccount(new AuthHttpRequest(getHttpAuthToken(respToken.body().string()), username, password)).execute();
                    if (!respAuth.isSuccessful()) {
                        listener.onLoginStatus(true);
                        return;
                    }

                    listener.onLoginStatus(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onLoginStatus(false);
                }
            }
        });
    }

    public void validateAccount(String username, String password, final OnLoginValidateAccount listener) {
        CredentialService service = ServiceFactory.createRetrofitService(CredentialService.class, CredentialService.CREDENTIALS_API);
        service.getPasswordCredentials(username, password, CredentialService.CLIENT_ID, CredentialService.GRANT_TYPE).enqueue(new Callback<Credentials>() {
            @Override
            public void onResponse(Call<Credentials> call, Response<Credentials> response) {
                listener.onValidate(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Credentials> call, Throwable t) {
                t.printStackTrace();
                listener.onValidate(false);
            }
        });
    }

    public void isLogged(final OnLoginCheckerListener listener ) {

        final AuthService service = ServiceFactory.createRetrofitService(AuthService.class, AuthService.AUTH_API);
        service.isLogged(Constant.mDomain).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onLogin(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                listener.onLogin(false);
            }
        });
    }

    public static String getHttpAuthToken(String data) {
        if (data.isEmpty())
            throw new IllegalArgumentException("Empty data for get http token");

        int firstIndex = data.indexOf("data.token(") + 12;
        if (firstIndex == -1)
            throw new IllegalStateException("Not found first index for get http token");

        return data.substring(firstIndex, data.indexOf("');", firstIndex));
    }
}
