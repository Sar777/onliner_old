package by.onliner.newsonlinerby;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ViewNewsActivity extends AppCompatActivity implements View.OnClickListener {
    private static String ASYNC_CLIENT_TAG = "FULL_VIEW_NEWS";

    public static String INTENT_URL_TAG = "URL";

    private AsyncHttpClient mClient = new AsyncHttpClient();

    // Views
    private ProgressBar mProgressBar;
    private View mContainerNews;
    private Button mButtonComment;

    private ContentNews mContent;

    private int mShortAnimationDuration;

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
        mProgressBar = (ProgressBar)findViewById(R.id.progressBarLoading);
        mContainerNews = (View)findViewById(R.id.scrollView_content_news);
        mContainerNews.setVisibility(View.GONE);

        mButtonComment = (Button)findViewById(R.id.btn_comment_full_news);
        mButtonComment.setOnClickListener(this);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Intent intent = getIntent();
        String url = intent.getStringExtra(TabBase.INTENT_URL_TAG);

        mClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mContent = new FullNewsParser().parse(new String(responseBody));
                new InitializeActivityTask().execute();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mProgressBar.setVisibility(View.GONE);
            }
        }).setTag(ASYNC_CLIENT_TAG);
    }

    private void VisibleNewsCountainer() {
        mContainerNews.setAlpha(0f);
        mContainerNews.setVisibility(View.VISIBLE);

        mContainerNews.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(null);

        mProgressBar.animate().alpha(0f).setDuration(mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_full_news: {
                Intent intent = new Intent(App.getContext(), CommentsActivity.class);
                intent.putExtra(INTENT_URL_TAG, mContent.getHeader().getUrl());
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }

    class InitializeActivityTask extends AsyncTask<Void, Void, View> {
        @Override
        protected View doInBackground(Void... params) {
            return new BodyBuilder(mContent).build(null);
        }

        @Override
        protected void onPostExecute(View layout) {
            super.onPostExecute(layout);

            ((ViewGroup)findViewById(R.id.l_content_news)).addView(layout, 1);

            ((TextView)findViewById(R.id.tv_full_view_date)).setText(mContent.getHeader().getPostDate());
            ((TextView)findViewById(R.id.tv_full_view_comments)).setText(mContent.getHeader().getComments().toString());
            ((TextView)findViewById(R.id.tv_full_view_views)).setText(mContent.getHeader().getViews().toString());
            ((TextView)findViewById(R.id.tv_full_view_title)).setText(mContent.getHeader().getTitle());

            Picasso.with(App.getContext()).
                    load(mContent.getHeader().getImage()).
                    error(R.drawable.ic_broken_image).
                    into((ImageView) findViewById(R.id.i_full_news_image));

            VisibleNewsCountainer();
        }
    }
}
