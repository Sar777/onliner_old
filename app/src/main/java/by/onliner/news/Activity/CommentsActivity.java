package by.onliner.news.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

import by.onliner.news.Adapters.CommentsListAdapter;
import by.onliner.news.Common.Common;
import by.onliner.news.Parser.Parsers.CommentsParser;
import by.onliner.news.R;
import by.onliner.news.Services.Comment.CommentResponse;
import by.onliner.news.Services.Comment.CommentService;
import by.onliner.news.Services.ServiceFactory;
import by.onliner.news.Structures.Comments.Comment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Просмотр комментариев
 */
public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Comment> mComments = new ArrayList<>();

    private CommentsListAdapter mCommentListAdapter;

    // Views
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBarLoading;

    private Button mButtonMessage;
    private EditText mEditTextMessage;
    private ProgressBar mProgressBarCommentSending;

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
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(horizontalLayoutManager);

        mCommentListAdapter = new CommentsListAdapter(this, mProject, mComments);
        mRecyclerView.setAdapter(mCommentListAdapter);

        mProgressBarLoading = (ProgressBar)findViewById(R.id.pb_comment_list);
        mProgressBarLoading.setVisibility(View.GONE);

        mEditTextMessage = (EditText) findViewById(R.id.et_comment_message);

        mButtonMessage = (Button) findViewById(R.id.bt_send_message);
        mButtonMessage.setOnClickListener(this);

        mProgressBarCommentSending = (ProgressBar) findViewById(R.id.pb_comment_sending);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send_message:
                sendComment();
                break;
            default:
                break;
        }
    }

    private void sendComment() {
        String message = mEditTextMessage.getText().toString();
        if (message.isEmpty())
            return;

        mEditTextMessage.setText("");
        showSendMessageButton(true);

        CommentService service = ServiceFactory.createRetrofitService(CommentService.class, Common.getUrlByProject(mProject));
        service.sendCommentMessage(message, mId.toString()).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                showSendMessageButton(false);

                // Запрос не прошел
                if (!response.isSuccessful()) {
                    Snackbar.make(mButtonMessage, R.string.string_comment_unknown_error, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                CommentResponse commentResponse = response.body();

                // Возможно аккаунт заблокирован
                if (commentResponse.getError() != null) {
                    Snackbar.make(mButtonMessage, commentResponse.getError(), Snackbar.LENGTH_LONG).show();
                    return;
                }

                // Добавление в список
                mCommentListAdapter.getResource().add(new CommentsParser().parse(commentResponse.getComment()).get(commentResponse.getId()));
                mCommentListAdapter.notifyItemInserted(mCommentListAdapter.getItemCount());
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                t.printStackTrace();
                Snackbar.make(mButtonMessage, R.string.string_comment_unknown_error, Snackbar.LENGTH_SHORT).show();
                showSendMessageButton(false);
            }
        });
    }

    private void showSendMessageButton(boolean hide) {
        if (hide) {
            mButtonMessage.setVisibility(View.GONE);
            mProgressBarCommentSending.setVisibility(View.VISIBLE);
            return;
        }

        mButtonMessage.setVisibility(View.VISIBLE);
        mProgressBarCommentSending.setVisibility(View.GONE);
    }
}