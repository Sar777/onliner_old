package by.onliner.news.activity;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import by.onliner.news.App;
import by.onliner.news.Fragments.tabs.TabBase;
import by.onliner.news.R;
import by.onliner.news.adapters.NewsContentAdapter;
import by.onliner.news.loaders.AsyncNewsCommentLoader;
import by.onliner.news.loaders.AsyncNewsContentLoader;
import by.onliner.news.managers.FavoritesNewsMgr;
import by.onliner.news.structures.comments.Comment;
import by.onliner.news.structures.news.News;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Просмотр отдельной новости
 */
public class ViewNewsActivity extends AppCompatActivity implements View.OnClickListener, LoaderCallbacks<ArrayList<ViewObject>> {

    public static String INTENT_PROJECT_TAG = "PROJECT";
    public static String INTENT_NEWS_ID_TAG = "NEWS_ID";
    public static String INTENT_COMMENTS_TAG = "COMMENTS";

    private static final int LOADER_CONTENT_ID = 1;
    private static final int LOADER_COMMENTS_ID = 2;

    private News mNews;

    private String mTitleString;
    private String mURL;
    private String mProject;

    // Adapters
    private NewsContentAdapter mNewsContentAdapter;

    private ArrayList<ViewObject> mViewObjects;

    // Views
    private TextView mTitle;
    private Button mButtonComment;
    private Button mButtonRepeat;
    private ProgressBar mProgressBar;
    private ViewGroup mBaseLayout;
    private ViewGroup mRepeatGroup;
    private RecyclerView mRecyclerContent;

    // Menu
    private MenuItem mItemFavorite;
    private MenuItem mItemRemoveFavorite;

    private ArrayList<Comment> mComments;

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
        mBaseLayout = (ViewGroup)findViewById(R.id.l_view_news_content);
        mBaseLayout.setVisibility(View.GONE);

        mProgressBar = (ProgressBar)findViewById(R.id.pb_news_list_loading);
        mProgressBar.setVisibility(View.VISIBLE);

        mRepeatGroup = (ViewGroup)findViewById(R.id.l_view_news_repeat);

        mButtonComment = (Button)findViewById(R.id.bt_view_news_comments);
        mButtonComment.setOnClickListener(this);

        mButtonRepeat = (Button)findViewById(R.id.btn_load_repeat);
        mButtonRepeat.setOnClickListener(this);

        mRecyclerContent = (RecyclerView)findViewById(R.id.recycler_news_content);
        LinearLayoutManager verticalLinearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerContent.setLayoutManager(verticalLinearLayout);

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Intent intent = getIntent();
        mURL = intent.getStringExtra(TabBase.INTENT_URL_TAG);
        mProject = intent.getStringExtra(TabBase.INTENT_PROJECT_TAG);
        mTitleString = intent.getStringExtra(TabBase.INTENT_TITLE_TAG);

        boolean initLoader = true;

        // Восстановление активити
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("NEWS") && savedInstanceState.containsKey("COMMENTS")) {
                mNews = savedInstanceState.getParcelable("NEWS");

                mComments = savedInstanceState.getParcelableArrayList("COMMENTS");
                mViewObjects = savedInstanceState.getParcelableArrayList("VIEWS_OBJECTS");

                mNewsContentAdapter = new NewsContentAdapter(this, mViewObjects);
                mRecyclerContent.setAdapter(mNewsContentAdapter);

                mProgressBar.setVisibility(View.GONE);
                mBaseLayout.setVisibility(View.VISIBLE);
                mButtonComment.setVisibility(View.VISIBLE);

                initLoader = false;
            }
        }

        if (initLoader) {
            Bundle bundle = new Bundle();
            bundle.putString("URL", mURL);
            bundle.putString("PROJECT", mProject);
            bundle.putString("TITLE", mTitleString);
            getLoaderManager().initLoader(LOADER_CONTENT_ID, bundle, this);
        }

        mTitle = (TextView) findViewById(R.id.tv_view_news_title);
        mTitle.setText(mTitleString);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getLoaderManager().destroyLoader(LOADER_CONTENT_ID);

        if (mNews != null) {
            outState.putParcelable("NEWS", mNews);
            outState.putParcelableArrayList("VIEWS_OBJECTS", mViewObjects);
        }

        if (mComments != null)
            outState.putParcelableArrayList("COMMENTS", mComments);
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

            mViewObjects = views;
            mNews = ((AsyncNewsContentLoader)loader).getNews();

            mNewsContentAdapter = new NewsContentAdapter(this, mViewObjects);
            mRecyclerContent.setAdapter(mNewsContentAdapter);

            // Запуск обработки комментариев
            loadingComments(mNews);

            updateActionBar();

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
            case R.id.bt_view_news_comments: {
                Intent intent = new Intent(this, CommentsActivity.class);
                intent.putExtra(INTENT_NEWS_ID_TAG, mNews.getAttributes().getId());
                intent.putExtra(INTENT_PROJECT_TAG, mNews.getAttributes().getProject());
                intent.putParcelableArrayListExtra(INTENT_COMMENTS_TAG, mComments);
                startActivity(intent);
                break;
            }
            case R.id.btn_load_repeat: {
                Bundle bundle = new Bundle();
                bundle.putString("URL", mNews.getAttributes().getUrl());
                getLoaderManager().restartLoader(LOADER_CONTENT_ID, bundle, this).forceLoad();
                break;
            }
            default:
                break;
        }
    }

    private void loadingComments(News news) {
        Bundle bundle = new Bundle();
        bundle.putString("Html", news.getContent());
        bundle.putString("Project", news.getAttributes().getProject());
        bundle.putString("PostId", news.getAttributes().getId().toString());
        getLoaderManager().restartLoader(LOADER_COMMENTS_ID, bundle, new LoaderCallbacks<LinkedHashMap<String, Comment>>() {
            @Override
            public Loader<LinkedHashMap<String, Comment>> onCreateLoader(int i, Bundle bundle) {
                return new AsyncNewsCommentLoader(App.getContext(), bundle);
            }

            @Override
            public void onLoadFinished(Loader<LinkedHashMap<String, Comment>> loader, LinkedHashMap<String, Comment> result) {
                mComments = new ArrayList<>(result.values());
                mButtonComment.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoaderReset(Loader<LinkedHashMap<String, Comment>> loader) { }
        }).forceLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_news_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Loader<ArrayList<ViewObject>> loader = getLoaderManager().getLoader(LOADER_CONTENT_ID);
        if (loader != null)
            loader.forceLoad();

        mItemFavorite = menu.findItem(R.id.action_favorites);
        mItemRemoveFavorite = menu.findItem(R.id.action_remove_favorites);
        updateActionBar();

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                FavoritesNewsMgr.getInstance().saveFavorite(mNews);
                mItemFavorite.setVisible(false);
                mItemRemoveFavorite.setVisible(true);
                Toast.makeText(this, R.string.add_favorite_news, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_remove_favorites:
                FavoritesNewsMgr.getInstance().deleteFavorite(mNews);
                mItemFavorite.setVisible(true);
                mItemRemoveFavorite.setVisible(false);
                Toast.makeText(this, R.string.remove_favorite_news, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

    private void updateActionBar() {
        if (mNews == null)
            return;

        // Избранная новость
        if (FavoritesNewsMgr.getInstance().isFavorite(mNews.getAttributes().getId())) {
            mItemRemoveFavorite.setVisible(true);
            mItemFavorite.setVisible(false);
        }
        else
            mItemFavorite.setVisible(true);
    }
}
