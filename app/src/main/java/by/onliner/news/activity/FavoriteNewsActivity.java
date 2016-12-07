package by.onliner.news.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import by.onliner.news.adapters.NewsFavoritesListAdapter;
import by.onliner.news.R;

public class FavoriteNewsActivity extends AppCompatActivity {

    // Views
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private NewsFavoritesListAdapter mNewsFavoritesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_favorite_news);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.pb_favorites_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_favorites_news);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(horizontalLayoutManager);

        mNewsFavoritesListAdapter = new NewsFavoritesListAdapter(this);
        mRecyclerView.setAdapter(mNewsFavoritesListAdapter);

        mProgressBar.setVisibility(View.GONE);
    }
}
