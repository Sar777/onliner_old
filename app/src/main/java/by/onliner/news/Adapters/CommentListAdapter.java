package by.onliner.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.news.Activity.AuthActivity;
import by.onliner.news.App;
import by.onliner.news.Common.Common;
import by.onliner.news.Factory.Comment.CommentQuoteFactory;
import by.onliner.news.Listeners.OnLikeCommentListener;
import by.onliner.news.Managers.LikeMgr;
import by.onliner.news.R;
import by.onliner.news.Services.Likes.LikeCommentResponse;
import by.onliner.news.Structures.Comments.Comment;
import by.onliner.news.Transforms.CircleTransform;
import cz.msebera.android.httpclient.HttpStatus;

/**
 * Адаптере для отображения комментариев к новости
 */
public class CommentListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Comment> mObjects;
    private String mProject;

    // Views
    private Button mButtonLike;
    private Button mButtonDeslike;
    private ProgressBar mProgressBar;
    private TextView mTextViewAuthor;
    private TextView mTextViewDate;
    private TextView mTextViewTopComment;
    private TextView mTextViewLikeCount;
    private ViewGroup mViewGroupCommentText;
    private ViewGroup mViewGroupLikeGroup;
    private ImageView mImageViewAvatar;
    private ImageView mImageViewLikeBest;
    private ImageView mImageViewLikeDefault;

    public CommentListAdapter(Context context, ArrayList<Comment> comments, String project) {
        mContext = context;
        mObjects = comments;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mProject = project;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_item_comment, parent, false);
        }

        mButtonLike = (Button) view.findViewById(R.id.bt_like_comment);
        mButtonLike.setOnClickListener(this);

        mButtonDeslike = (Button) view.findViewById(R.id.bt_deslike_comment);
        mButtonDeslike.setOnClickListener(this);

        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_like_progress);
        mTextViewAuthor = (TextView) view.findViewById(R.id.tv_comment_author);
        mTextViewDate = (TextView) view.findViewById(R.id.tv_comment_date);
        mTextViewTopComment = (TextView) view.findViewById(R.id.tv_top_comment);
        mTextViewLikeCount = (TextView) view.findViewById(R.id.tv_comment_likes_count);
        mViewGroupCommentText = ((ViewGroup) view.findViewById(R.id.l_comment_text));
        mViewGroupLikeGroup = ((ViewGroup) view.findViewById(R.id.l_like_group));
        mImageViewAvatar = (ImageView)view.findViewById(R.id.img_avatar);
        mImageViewLikeBest = (ImageView)view.findViewById(R.id.img_like_best);
        mImageViewLikeDefault = (ImageView)view.findViewById(R.id.img_like_default);

        Comment comment = getComment(position);

        mTextViewAuthor.setText(comment.getAuthor().getName());
        mTextViewDate.setText(comment.getDate());

        if (comment.getLikes().isBest() && position == 0) {
            mTextViewTopComment.setVisibility(View.VISIBLE);
            mButtonLike.setVisibility(View.GONE);
        }
        else {
            mTextViewTopComment.setVisibility(View.GONE);
            mButtonLike.setVisibility(View.VISIBLE);
        }

        if (comment.getLikes().getCount() > 0) {
            mViewGroupLikeGroup.setVisibility(View.VISIBLE);
            mTextViewLikeCount.setText(comment.getLikes().getCount().toString());
            if (comment.getLikes().isBest()) {
                mImageViewLikeBest.setVisibility(View.VISIBLE);
                mImageViewLikeDefault.setVisibility(View.GONE);
            }
            else {
                mImageViewLikeDefault.setVisibility(View.VISIBLE);
                mImageViewLikeBest.setVisibility(View.GONE);
            }
        }
        else
            mViewGroupLikeGroup.setVisibility(View.GONE);

        mViewGroupCommentText.removeAllViews();

        TextView commentTextView = new TextView(App.getContext());
        commentTextView.setTextColor(Color.BLACK);
        commentTextView.setText(Common.fromHtml(comment.getText()));
        commentTextView.setPadding(20, 40, 30, 0);

        // Формирование цитаты в комментарии
        if (comment.getQuote() != null) {
            View quoteView = new CommentQuoteFactory().create(comment.getQuote());
            if (quoteView != null) {
                mViewGroupCommentText.addView(quoteView, 0);
                commentTextView.setPadding(20, 0, 30, 0);
            }
        }

        mViewGroupCommentText.addView(commentTextView);

        mButtonLike.setTag(comment.getId());
        mButtonDeslike.setTag(comment.getId());

        if (comment.getLikes().getIsLike() != null && comment.getLikes().getIsLike()) {
            mButtonLike.setVisibility(View.GONE);
            mButtonDeslike.setVisibility(View.VISIBLE);
        }
        else {
            mButtonLike.setVisibility(View.VISIBLE);
            mButtonDeslike.setVisibility(View.GONE);
        }

        if (!comment.getAvatarURL().isEmpty())
            Picasso.with(mContext).
                   load(comment.getAvatarURL()).
                   error(R.drawable.ic_broken_image).
                   transform(new CircleTransform()).
                   into(mImageViewAvatar);

        return view;
    }

    Comment getComment(int position) {
        return ((Comment) getItem(position));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_like_comment:
                likeComment(view.getTag().toString());
                break;
            case R.id.bt_deslike_comment:
                deslikeComment(view.getTag().toString());
                break;
            default:
                break;
        }
    }

    private void authStartActivity() {
        Intent intent = new Intent(mButtonLike.getContext(), AuthActivity.class);
        mButtonLike.getContext().startActivity(intent);
    }

    private void likeComment(String comment_id) {
        likeButtonView(true, false);

        LikeMgr.getInstance().likeComment(comment_id, mProject, new OnLikeCommentListener() {
            @Override
            public void OnResponse(int errCode, LikeCommentResponse response) {
                if (errCode == HttpStatus.SC_UNAUTHORIZED) {
                    authStartActivity();
                    likeButtonView(true, true);
                    return;
                }

                if (response == null) {
                    Snackbar.make(mButtonLike, R.string.like_comment_error, Snackbar.LENGTH_LONG).show();
                    likeButtonView(true, true);
                    return;
                }

                if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                    Snackbar.make(mButtonLike, response.getErrors(), Snackbar.LENGTH_LONG).show();
                    likeButtonView(true, true);
                    return;
                }

                mTextViewLikeCount.setText(response.getLikes());
            }
        });
    }

    private void deslikeComment(String comment_id) {
        likeButtonView(false, false);

        LikeMgr.getInstance().deslikeComment(comment_id, mProject, new OnLikeCommentListener() {
            @Override
            public void OnResponse(int errCode, LikeCommentResponse response) {
                if (errCode == HttpStatus.SC_UNAUTHORIZED) {
                    authStartActivity();
                    likeButtonView(false, true);
                    return;
                }

                if (response == null) {
                    Snackbar.make(mButtonLike, R.string.like_comment_error, Snackbar.LENGTH_LONG).show();
                    likeButtonView(false, true);
                    return;
                }

                if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                    Snackbar.make(mButtonLike, response.getErrors(), Snackbar.LENGTH_LONG).show();
                    likeButtonView(false, true);
                    return;
                }

                mTextViewLikeCount.setText(response.getLikes());
            }
        });
    }

    private void likeButtonView(boolean like, boolean enable) {
        if (enable) {
            if (like)
                mButtonLike.setVisibility(View.VISIBLE);
            else
                mButtonDeslike.setVisibility(View.VISIBLE);

            mProgressBar.setVisibility(View.GONE);
        }
        else {
            if (like)
                mButtonLike.setVisibility(View.GONE);
            else
                mButtonDeslike.setVisibility(View.GONE);

            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}