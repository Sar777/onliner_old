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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import by.onliner.newsonlinerby.Asynchronous.AsyncCommentParser;
import by.onliner.newsonlinerby.Builder.News.BodyBuilder;
import by.onliner.newsonlinerby.Listeners.ResponseListener;
import by.onliner.newsonlinerby.Listeners.ViewNewsListener;
import by.onliner.newsonlinerby.Managers.LikeMgr;
import by.onliner.newsonlinerby.Managers.NewsMgr;
import by.onliner.newsonlinerby.Parser.Parsers.BodyNewsParser;
import by.onliner.newsonlinerby.Structures.Comments.Comment;
import by.onliner.newsonlinerby.Structures.Comments.Like;
import by.onliner.newsonlinerby.Structures.News.News;
import by.onliner.newsonlinerby.Tabs.TabBase;
import cz.msebera.android.httpclient.HttpStatus;

public class ViewNewsActivity extends AppCompatActivity implements View.OnClickListener {

    public static String INTENT_URL_TAG = "URL";
    public static String INTENT_COMMENTS_TAG = "COMMENTS";
    public static String INTENT_PROJECT_TAG = "PROJECT";

    private AsyncHttpClient mClient = new AsyncHttpClient();

    // Views
    private ProgressBar mProgressBar;
    private View mContainerNews;
    private Button mButtonComment;
    private ViewGroup mRepeatGroup;

    private News mContent;
    private LinkedHashMap<Integer, Comment> mComments;
    private String mUrl;

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

        mComments = new LinkedHashMap<>();

        // Views
        mProgressBar = (ProgressBar)findViewById(R.id.pb_news_list_loading);
        mRepeatGroup = (ViewGroup)findViewById(R.id.l_view_news_repeat);

        mContainerNews = findViewById(R.id.scrollView_content_news);
        mContainerNews.setVisibility(View.GONE);

        mButtonComment = (Button)findViewById(R.id.btn_comment_full_news);
        mButtonComment.setOnClickListener(this);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra(TabBase.INTENT_URL_TAG);

        LoadingContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_full_news: {
                Intent intent = new Intent(App.getContext(), CommentsActivity.class);
                intent.putExtra(INTENT_URL_TAG, mContent.getHeader().getUrl());
                intent.putExtra(INTENT_COMMENTS_TAG, new ArrayList<Comment>(mComments.values()));
                intent.putExtra(INTENT_PROJECT_TAG, mContent.getAttributes().getProject());
                startActivity(intent);
                break;
            }
            case R.id.btn_load_repeat: {
                LoadingContent();
                break;
            }
            default:
                break;
        }
    }

    /**
     * Загрузка и обработка новости
     */
    private void LoadingContent() {
        mRepeatGroup.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        NewsMgr.getInstance().getNewsByUrl(mUrl, new ViewNewsListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                if (statusCode == HttpStatus.SC_OK) {
                    mContent = new BodyNewsParser().parse(response);
                    new AsyncBodyBuilder().execute();

                    // Парсинг комментариев
                    new AsyncCommentParser(response, new ResponseListener<LinkedHashMap<Integer, Comment>>() {
                        @Override
                        public void onResponse(LinkedHashMap response) {
                            mComments = response;

                            // Запрос на получение спискай лайков
                            LikeMgr.getInstance().getAsyncLikes(mContent.getLikesAPIUrl(), new ResponseListener<ArrayList<Like>>() {
                                @Override
                                public void onResponse(ArrayList<Like> response) {
                                    for (Like like : response) {
                                        Comment comment = mComments.get(like.getCommentId());
                                        if (comment != null)
                                            comment.setLikes(like);
                                    }
                                }
                            });
                        }
                    }).execute();
                }
                // Ошибка при загрузке
                else {
                    mRepeatGroup.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    // Асинхронный разбор и постройка Layout c содержимым новости
    class AsyncBodyBuilder extends AsyncTask<Void, Void, View> {
        @Override
        protected View doInBackground(Void... params) {
            // Формирование Laoyut
            return new BodyBuilder(ViewNewsActivity.this, mContent).build(null);
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

            VisibleNewsContainer();
        }
    }

    private void VisibleNewsContainer() {
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
}
