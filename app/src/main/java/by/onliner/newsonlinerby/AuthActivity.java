package by.onliner.newsonlinerby;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

import static by.onliner.newsonlinerby.Constants.Constant.mProfileURL;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_auth);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        App.setWebView((WebView)findViewById(R.id. webview_auth));
        App.getWebView().getSettings().setJavaScriptEnabled(true);
        App.getWebView().setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            public void onPageFinished(WebView view, String url) {
                String cookies = CookieManager.getInstance().getCookie(url);
                Log.e("ORION-REST", "All the cookies in a string:" + cookies);

                PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
                AsyncHttpClient client = new AsyncHttpClient();
                client.setCookieStore(myCookieStore);

                client.get("https://profile.onliner.by", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ORION-REST", String.valueOf(new String(responseBody).indexOf("OrionDev")));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });

        App.getWebView().loadUrl("https://profile.onliner.by");
    }
}
