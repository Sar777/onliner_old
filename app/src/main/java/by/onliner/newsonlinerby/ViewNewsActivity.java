package by.onliner.newsonlinerby;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import by.onliner.newsonlinerby.Parser.Parsers.FullNewsParser;
import by.onliner.newsonlinerby.Structures.ContentNews;
import by.onliner.newsonlinerby.Tabs.TabBase;
import cz.msebera.android.httpclient.Header;

public class ViewNewsActivity extends AppCompatActivity {
    private static String ASYNC_CLIENT_TAG = "FULL_VIEW_NEWS";

    private AsyncHttpClient client = new AsyncHttpClient();

    private ProgressBar progressBar;
    private ImageLoader imageLoader;

    private int fragmentId; // for back

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_news_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(MainActivity.imageLoaderConfig);

        // Views
        progressBar = (ProgressBar)findViewById(R.id.progressBarLoading);

        Intent intent = getIntent();
        String url = intent.getStringExtra(TabBase.INTENT_URL_TAG);

        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ContentNews content = new FullNewsParser().parse(new String(responseBody));
                InitialViewElements(content);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.GONE);
            }
        }).setTag(ASYNC_CLIENT_TAG);
    }

    private void InitialViewElements(ContentNews content) {
        ((TextView)findViewById(R.id.tv_full_view_date)).setText(content.getHeader().getPostDate());
        ((TextView)findViewById(R.id.tv_full_view_comments)).setText(content.getHeader().getComments().toString());
        ((TextView)findViewById(R.id.tv_full_view_views)).setText(content.getHeader().getViews().toString());
        ((TextView)findViewById(R.id.tv_full_view_title)).setText(content.getHeader().getTitle());
        //((TextView)findViewById(R.id.tv_full_view_text)).setText(Html.fromHtml(content.getContent()));

        imageLoader.displayImage(content.getHeader().getImage(), (ImageView)findViewById(R.id.i_full_news_image), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                view.getRootView().findViewById(R.id.pb_view_image).setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                view.getRootView().findViewById(R.id.pb_view_image).setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                view.getRootView().findViewById(R.id.pb_view_image).setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                view.getRootView().findViewById(R.id.pb_view_image).setVisibility(View.GONE);
            }
        });

        ((RelativeLayout)findViewById(R.id.l_container_news)).setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
    }
}
