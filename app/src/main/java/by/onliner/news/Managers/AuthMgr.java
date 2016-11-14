package by.onliner.news.Managers;

import com.loopj.android.http.AsyncHttpClient;

import by.onliner.news.Listeners.AuthResponseListener;

/**
 * Created by orion on 24.10.16.
 */
public class AuthMgr {
    private static String ACCEPT_TYPE = "application/json, text/javascript, */*; q=0.01";
    private static String mAuthURL = "https://user.api.onliner.by/login";

    private static AuthMgr ourInstance = new AuthMgr();

    private AsyncHttpClient mClient = new AsyncHttpClient(true, 80, 443);

    private AuthMgr() {
        mClient.addHeader("Accept", ACCEPT_TYPE);
        mClient.addHeader("Content-Type", "application/json");
    }

    public static AuthMgr getInstance() {
        return ourInstance;
    }

    public void getAuthToken() {
    }

    public void authAccount(String username, String password, final AuthResponseListener listener) {
    }
}
