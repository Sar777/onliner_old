package by.onliner.news.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import by.onliner.news.Adapters.CommentsListAdapter;
import by.onliner.news.R;
import by.onliner.news.Structures.Comments.Comment;

/**
 * Просмотр комментариев
 */
public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Comment> mComments = new ArrayList<>();

    private CommentsListAdapter mCommentListAdapter;

    // Views
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBarLoading;
    private FloatingActionButton mFloatingActionButton;

    @NonNull
    private Integer mId;
    @NonNull
    private String mProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment_list);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mComments = (ArrayList<Comment>)( getIntent().getSerializableExtra(ViewNewsActivity.INTENT_COMMENTS_TAG));
        mProject = getIntent().getStringExtra(ViewNewsActivity.INTENT_PROJECT_TAG);
        mId = getIntent().getIntExtra(ViewNewsActivity.INTENT_NEWS_ID_TAG, -1);

        // Топ коммпентарий
        for (Comment comment : mComments) {
            if (comment.getLikes().isBest()) {
                mComments.add(0, comment);
                break;
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_comments_list);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(horizontalLayoutManagaer);

        mCommentListAdapter = new CommentsListAdapter(this, mProject, mComments);
        mRecyclerView.setAdapter(mCommentListAdapter);

        mProgressBarLoading = (ProgressBar)findViewById(R.id.pb_comment_list);
        mProgressBarLoading.setVisibility(View.GONE);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_comments);
        mFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_comments:
                break;
            default:
                break;
        }
    }
}