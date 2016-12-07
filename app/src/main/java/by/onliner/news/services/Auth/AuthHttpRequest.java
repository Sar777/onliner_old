package by.onliner.news.services.Auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by orion on 19.11.16.
 */

public class AuthHttpRequest {
    @SerializedName("token")
    String mToken;
    @SerializedName("login")
    String mLogin;
    @SerializedName("password")
    String mPassword;

    public AuthHttpRequest(String token, String login, String password) {
        this.mToken = token;
        this.mLogin = login;
        this.mPassword = password;
    }

    public String getToken() {
        return mToken;
    }

    public String getLogin() {
        return mLogin;
    }

    public String getPassword() {
        return mPassword;
    }
}
