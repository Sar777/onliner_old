package by.onliner.newsonlinerby.Managers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Listeners.AuthResponseListener;
import cz.msebera.android.httpclient.Header;

/**
 * Created by orion on 24.10.16.
 */
public class AuthMgr {
    private static String ACCEPT_TYPE = "text/html, */*; q=0.01";
    private static String mAuthURL = "https://user.api.onliner.by/login";

    private static AuthMgr ourInstance = new AuthMgr();

    private AsyncHttpClient mClient = new AsyncHttpClient(true, 80, 443);

    private AuthMgr() {
        //mClient.addHeader("Content-Type", ACCEPT_TYPE);
        mClient.addHeader("Accept", ACCEPT_TYPE);
        mClient.setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0");
    }

    public static AuthMgr getInstance() {
        return ourInstance;
    }

    public void getAuthToken() {
        mClient.get(App.getContext(), mAuthURL, new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ORION", new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void authAccount(String username, String password, final AuthResponseListener listener) {


        mClient.post(App.getContext(), mAuthURL, new RequestParams(), new AsyncHttpResponseHandler() {
            AuthResponseListener authResponseListener = listener;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                authResponseListener.onAuthResponse(new String(responseBody));
                Log.e("ORION", new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                authResponseListener.onAuthResponse(new String(responseBody));
                Log.e("ORION", new String(responseBody));
            }
        });
    }
}
