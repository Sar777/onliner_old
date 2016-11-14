package by.onliner.news.Activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import by.onliner.news.App;
import by.onliner.news.R;

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

        App.setWebView((WebView) findViewById(R.id.webview_auth));
        App.getWebView().getSettings().setJavaScriptEnabled(true);
        App.getWebView().setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.e("ORION-REST", "OK-2");
                if(error.getPrimaryError()== SslError.SSL_UNTRUSTED){
                    handler.proceed();
                }else{
                    handler.proceed();
                }
            }

            public void onPageFinished(WebView view, String url) {
                String cookies = CookieManager.getInstance().getCookie(url);
                Log.e("ORION-REST", "All the cookies in a string:" + cookies);
            }
        });

        App.getWebView().loadUrl("http://tut.by");
    }
}
