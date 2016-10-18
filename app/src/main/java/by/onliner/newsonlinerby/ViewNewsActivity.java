package by.onliner.newsonlinerby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import by.onliner.newsonlinerby.Builder.News.BodyBuilder;
import by.onliner.newsonlinerby.Parser.Parsers.FullNewsParser;
import by.onliner.newsonlinerby.Structures.ContentNews;
import by.onliner.newsonlinerby.Tabs.TabBase;
import cz.msebera.android.httpclient.Header;

public class ViewNewsActivity extends AppCompatActivity {
    private static String ASYNC_CLIENT_TAG = "FULL_VIEW_NEWS";

    private AsyncHttpClient client = new AsyncHttpClient();

    private ProgressBar progressBar;

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

    private void InitialViewElements(final ContentNews content) {
        ((TextView)findViewById(R.id.tv_full_view_date)).setText(content.getHeader().getPostDate());
        ((TextView)findViewById(R.id.tv_full_view_comments)).setText(content.getHeader().getComments().toString());
        ((TextView)findViewById(R.id.tv_full_view_views)).setText(content.getHeader().getViews().toString());
        ((TextView)findViewById(R.id.tv_full_view_title)).setText(content.getHeader().getTitle());

        new BodyBuilder(content.getContent()).build(findViewById(R.id.l_body_news_text));

        Picasso.with(App.getContext())
                .load(content.getHeader().getImage())
                //   .placeholder(R.drawable.user_placeholder)
                //   .error(R.drawable.user_placeholder_error)
                .into((ImageView)findViewById(R.id.i_full_news_image));

        findViewById(R.id.l_container_news).setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
