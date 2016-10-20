package by.onliner.newsonlinerby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Adapters.CommentListAdapter;
import by.onliner.newsonlinerby.Structures.Comment;

public class CommentsActivity extends AppCompatActivity {
    private ArrayList<Comment> mComments = new ArrayList<>();
    private CommentListAdapter mCommentListAdapter;

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

        mCommentListAdapter = new CommentListAdapter(this, mComments);

        ListView lvMain = (ListView) findViewById(R.id.lv_comments);
        lvMain.setAdapter(mCommentListAdapter);
    }
}
