package by.onliner.newsonlinerby.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Adapters.CommentListAdapter;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.Comments.Comment;

/**
 * Просмотр комментариев
 */
public class CommentsActivity extends AppCompatActivity {
    private ArrayList<Comment> mComments = new ArrayList<>();

    private CommentListAdapter mCommentListAdapter;

    // Views
    private ListView lvMain;
    private ProgressBar mProgressBarLoading;

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

        // Топ коммпентарий
        for (Comment comment : mComments) {
            if (comment.getLikes().isBest()) {
                mComments.add(0, comment);
                break;
            }
        }

        mCommentListAdapter = new CommentListAdapter(this, mComments, mProject);

        lvMain = (ListView) findViewById(R.id.lv_comments_list);
        lvMain.setAdapter(mCommentListAdapter);

        mProgressBarLoading = (ProgressBar)findViewById(R.id.pb_comment_list);
        mProgressBarLoading.setVisibility(View.GONE);
    }
}