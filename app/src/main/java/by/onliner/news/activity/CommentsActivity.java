package by.onliner.news.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import by.onliner.news.App;
import by.onliner.news.R;
import by.onliner.news.adapters.CommentsListAdapter;
import by.onliner.news.common.Common;
import by.onliner.news.parser.parsers.CommentsParser;
import by.onliner.news.services.comment.CommentResponse;
import by.onliner.news.services.comment.CommentService;
import by.onliner.news.services.ServiceFactory;
import by.onliner.news.structures.comments.Comment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Просмотр комментариев
 */
public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Comment> mComments;

    private CommentsListAdapter mCommentListAdapter;

    // Views
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBarLoading;

    private Button mButtonMessage;
    private EditText mEditTextMessage;
    private ProgressBar mProgressBarCommentSending;

    @NonNull
    private String mProject;
    private Integer mId;

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

        mComments = getIntent().getParcelableArrayListExtra(ViewNewsActivity.INTENT_COMMENTS_TAG);
        mId = getIntent().getIntExtra(ViewNewsActivity.INTENT_NEWS_ID_TAG, -1);
        mProject = getIntent().getStringExtra(ViewNewsActivity.INTENT_PROJECT_TAG);

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
        if (message.isEmpty()) {
            Snackbar.make(mButtonMessage, R.string.string_comment_emtry, Snackbar.LENGTH_SHORT).show();
            return;
        }

        mEditTextMessage.setText("");
        showSendMessageButton(true);

        CommentService service = ServiceFactory.createRetrofitService(CommentService.class, Common.getUrlByProject(mProject));
        service.sendCommentMessage(message, mId).enqueue(new Callback<CommentResponse>() {
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

                // Редирект аккаунт заблокирован
                if (commentResponse.getRedirect() != null) {
                    Snackbar.make(mButtonMessage, commentResponse.getRedirect(), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Добавление в список
                mCommentListAdapter.getResource().add(new CommentsParser().parse(commentResponse.getComment()).get(commentResponse.getId()));
                mCommentListAdapter.notifyItemInserted(mCommentListAdapter.getItemCount());
                mRecyclerView.scrollToPosition(mCommentListAdapter.getItemCount() - 1);

                InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mRecyclerView.getWindowToken(), 0);

                Toast.makeText(App.getContext(), R.string.comment_added, Toast.LENGTH_SHORT).show();
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