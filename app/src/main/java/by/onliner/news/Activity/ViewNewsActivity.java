package by.onliner.news.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import by.onliner.news.Adapters.NewsContentAdapter;
import by.onliner.news.App;
import by.onliner.news.Fragments.Tabs.TabBase;
import by.onliner.news.Loaders.AsyncNewsContentLoader;
import by.onliner.news.R;
import by.onliner.news.Structures.Comments.Comment;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Просмотр отдельной новости
 */
public class ViewNewsActivity extends AppCompatActivity implements View.OnClickListener, LoaderCallbacks<ArrayList<ViewObject>> {

    public static String INTENT_URL_TAG = "URL";
    public static String INTENT_COMMENTS_TAG = "COMMENTS";
    public static String INTENT_PROJECT_TAG = "PROJECT";

    private static final int LOADER_CONTENT_ID = 1;
    private static final int LOADER_COMMENTS_ID = 2;

    private String mURL;
    private String mProjectId;

    // Adapters
    private NewsContentAdapter mNewsContentAdapter;

    // Views
    private ViewGroup mBaseLayout;
    private ProgressBar mProgressBar;
    private Button mButtonComment;
    private Button mButtonRepeat;
    private ViewGroup mRepeatGroup;
    private RecyclerView mRecyclerContent;

    private LinkedHashMap<Integer, Comment> mComments;

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
        mBaseLayout = (ViewGroup)findViewById(R.id.l_view_news_content);
        mBaseLayout.setVisibility(View.GONE);

        mProgressBar = (ProgressBar)findViewById(R.id.pb_news_list_loading);
        mProgressBar.setVisibility(View.VISIBLE);

        mRepeatGroup = (ViewGroup)findViewById(R.id.l_view_news_repeat);

        mButtonComment = (Button)findViewById(R.id.btn_comment_full_news);
        mButtonComment.setOnClickListener(this);

        mButtonRepeat = (Button)findViewById(R.id.btn_load_repeat);
        mButtonRepeat.setOnClickListener(this);

        mRecyclerContent = (RecyclerView)findViewById(R.id.recycler_news_content);
        LinearLayoutManager verticcalLinearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerContent.setLayoutManager(verticcalLinearLayout);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Intent intent = getIntent();
        mURL = intent.getStringExtra(TabBase.INTENT_URL_TAG);
        mProjectId = intent.getStringExtra(TabBase.INTENT_URL_PROJECT);

        Bundle bundle = new Bundle();
        bundle.putString("URL", mURL);
        getLoaderManager().initLoader(LOADER_CONTENT_ID, bundle, this).forceLoad();
    }

    @Override
    public Loader<ArrayList<ViewObject>> onCreateLoader(int id, Bundle args) {
        Loader<ArrayList<ViewObject>> loader = null;
        if (id == LOADER_CONTENT_ID) {
            mRepeatGroup.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);

            loader = new AsyncNewsContentLoader(this, args);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ViewObject>> loader, ArrayList<ViewObject> views) {
        if (loader.getId() == LOADER_CONTENT_ID) {
            if (views == null) {
                mRepeatGroup.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                return;
            }

            mNewsContentAdapter = new NewsContentAdapter(this, views);
            mRecyclerContent.setAdapter(mNewsContentAdapter);

            // Показ главного окна
            mBaseLayout.setAlpha(0f);
            mBaseLayout.setVisibility(View.VISIBLE);

            mBaseLayout.animate().alpha(1f).setDuration(mShortAnimationDuration).setListener(null);

            mProgressBar.animate().alpha(0f).setDuration(mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(View.GONE);
                };
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ViewObject>> loader) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_full_news: {
                Intent intent = new Intent(App.getContext(), CommentsActivity.class);
                intent.putExtra(INTENT_URL_TAG, mURL);
                intent.putExtra(INTENT_PROJECT_TAG, mProjectId);
                intent.putExtra(INTENT_COMMENTS_TAG, new ArrayList<>(mComments.values()));
                startActivity(intent);
                break;
            }
            case R.id.btn_load_repeat: {
                Bundle bundle = new Bundle();
                bundle.putString("URL", mURL);
                getLoaderManager().restartLoader(LOADER_CONTENT_ID, bundle, this).forceLoad();
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

//                if (statusCode == HttpStatus.SC_OK) {
//                    mNews = new BodyNewsParser().parse(response);
//                    new AsyncBodyBuilder().execute();
//
//                    // Парсинг комментариев
//                    new AsyncCommentParser(response, new CommentListListener() {
//                        @Override
//                        public void onResponse(LinkedHashMap response) {
//                            mComments = response;
//
//                            // Запрос на получение спискай лайков
//                            LikeMgr.getInstance().getAsyncLikes(mNews.getLikesAPIUrl(), new ResponseListener<ArrayList<Like>>() {
//                                @Override
//                                public void onResponse(ArrayList<Like> response) {
//                                    if (response == null)
//                                        return;
//
//                                    for (Like like : response) {
//                                        Comment comment = mComments.get(like.getCommentId());
//                                        if (comment != null)
//                                            comment.setLikes(like);
//                                    }
//                                }
//                            });
//                        }
//                    }).execute();
//                }
//                // Ошибка при загрузке
//                else {
//                    mRepeatGroup.setVisibility(View.VISIBLE);
//                    mProgressBar.setVisibility(View.GONE);
//                }
//            }
    }
}
