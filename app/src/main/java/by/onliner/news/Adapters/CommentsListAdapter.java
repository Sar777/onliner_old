package by.onliner.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by orion on 12.11.16.
 */

public class CommentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private String mProject;
    private ArrayList<Comment> mResource;

    private final int VIEW_TYPE_COMMENT_NORMAL = 0;
    private final int VIEW_TYPE_COMMENT_TOP = 1;

    public CommentsListAdapter(Context context, String project, ArrayList<Comment> resource) {
        this.mContext = context;
        this.mProject = project;
        this.mResource = resource;
    }

    @Override
    public int getItemViewType(int position) {
        return mResource.get(position).getLikes().isBest() && position == 0 ? VIEW_TYPE_COMMENT_TOP : VIEW_TYPE_COMMENT_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_COMMENT_TOP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comment_top, parent, false);
            return new ViewCommentTopHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comment, parent, false);
        return new ViewCommentHolder(view);
    }

    public class ViewCommentTopHolder extends RecyclerView.ViewHolder {
        // Views
        private TextView mTextViewAuthor;
        private TextView mTextViewDate;
        private TextView mTextViewLikeCount;
        private ViewGroup mViewGroupCommentText;
        private ImageView mImageViewAvatar;

        public ViewCommentTopHolder(View itemView) {
            super(itemView);

            mTextViewAuthor = (TextView) itemView.findViewById(R.id.tv_comment_author);
            mTextViewDate = (TextView) itemView.findViewById(R.id.tv_comment_date);
            mTextViewLikeCount = (TextView) itemView.findViewById(R.id.tv_comment_likes_count);
            mViewGroupCommentText = ((ViewGroup) itemView.findViewById(R.id.l_comment_text));
            mImageViewAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        }

        private void bindView(final ViewCommentTopHolder holder, final int position) {
            Comment comment = mResource.get(position);

            holder.mTextViewAuthor.setText(comment.getAuthor().getName());
            holder.mTextViewDate.setText(comment.getDate());
            holder.mTextViewLikeCount.setText(comment.getLikes().getCount().toString());

            holder.mViewGroupCommentText.removeAllViews();

            TextView commentTextView = new TextView(App.getContext());
            commentTextView.setTextColor(Color.BLACK);
            commentTextView.setText(Common.fromHtml(comment.getText()));
            commentTextView.setPadding(20, 40, 30, 0);

            // Формирование цитаты в комментарии
            if (comment.getQuote() != null) {
                View quoteView = new CommentQuoteFactory().create(comment.getQuote());
                if (quoteView != null) {
                    holder.mViewGroupCommentText.addView(quoteView, 0);
                    commentTextView.setPadding(20, 0, 30, 0);
                }
            }

            holder.mViewGroupCommentText.addView(commentTextView);

            if (!comment.getAvatarURL().isEmpty())
                Picasso.with(mContext).
                        load(comment.getAvatarURL()).
                        error(R.drawable.ic_broken_image).
                        transform(new CircleTransform()).
                        into(holder.mImageViewAvatar);
        }
    }

    public class ViewCommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Views
        private Button mButtonLike;
        private Button mButtonDeslike;
        private ProgressBar mProgressBar;
        private TextView mTextViewAuthor;
        private TextView mTextViewDate;
        private TextView mTextViewLikeCount;
        private ViewGroup mViewGroupCommentText;
        private ViewGroup mViewGroupLikeGroup;
        private ImageView mImageViewAvatar;
        private ImageView mImageViewLikeBest;
        private ImageView mImageViewLikeDefault;

        public ViewCommentHolder(View itemView)  {
            super(itemView);

            mButtonLike = (Button) itemView.findViewById(R.id.bt_like_comment);
            mButtonLike.setOnClickListener(this);

            mButtonDeslike = (Button) itemView.findViewById(R.id.bt_deslike_comment);
            mButtonDeslike.setOnClickListener(this);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_like_progress);
            mTextViewAuthor = (TextView) itemView.findViewById(R.id.tv_comment_author);
            mTextViewDate = (TextView) itemView.findViewById(R.id.tv_comment_date);
            mTextViewLikeCount = (TextView) itemView.findViewById(R.id.tv_comment_likes_count);
            mViewGroupCommentText = ((ViewGroup) itemView.findViewById(R.id.l_comment_text));
            mViewGroupLikeGroup = ((ViewGroup) itemView.findViewById(R.id.l_like_group));
            mImageViewAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            mImageViewLikeBest = (ImageView) itemView.findViewById(R.id.img_like_best);
            mImageViewLikeDefault = (ImageView) itemView.findViewById(R.id.img_like_default);
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

        private void bindView(final ViewCommentHolder holder, final int position) {
            Comment comment = mResource.get(position);

            holder.mTextViewAuthor.setText(comment.getAuthor().getName());
            holder.mTextViewDate.setText(comment.getDate());
            holder.mViewGroupLikeGroup.setTag(position);

            if (comment.getLikes().getCount() > 0) {
                holder.mViewGroupLikeGroup.setVisibility(View.VISIBLE);
                holder.mTextViewLikeCount.setText(comment.getLikes().getCount().toString());
                if (comment.getLikes().isBest()) {
                    holder.mImageViewLikeBest.setVisibility(View.VISIBLE);
                    holder.mImageViewLikeDefault.setVisibility(View.GONE);
                }
                else {
                    holder.mImageViewLikeDefault.setVisibility(View.VISIBLE);
                    holder.mImageViewLikeBest.setVisibility(View.GONE);
                }
            }
            else
                holder.mViewGroupLikeGroup.setVisibility(View.GONE);

            holder.mViewGroupCommentText.removeAllViews();

            TextView commentTextView = new TextView(App.getContext());
            commentTextView.setTextColor(Color.BLACK);
            commentTextView.setText(Common.fromHtml(comment.getText()));
            commentTextView.setPadding(20, 40, 30, 0);

            // Формирование цитаты в комментарии
            if (comment.getQuote() != null) {
                View quoteView = new CommentQuoteFactory().create(comment.getQuote());
                if (quoteView != null) {
                    holder.mViewGroupCommentText.addView(quoteView, 0);
                    commentTextView.setPadding(20, 0, 30, 0);
                }
            }

            holder.mViewGroupCommentText.addView(commentTextView);

            holder.mButtonLike.setTag(comment.getId());
            holder.mButtonDeslike.setTag(comment.getId());

            if (comment.getLikes().getIsLike() != null && comment.getLikes().getIsLike()) {
                holder.mButtonLike.setVisibility(View.GONE);
                holder.mButtonDeslike.setVisibility(View.VISIBLE);
            }
            else {
                holder.mButtonLike.setVisibility(View.VISIBLE);
                holder.mButtonDeslike.setVisibility(View.GONE);
            }

            if (!comment.getAvatarURL().isEmpty())
                Picasso.with(mContext).
                        load(comment.getAvatarURL()).
                        error(R.drawable.ic_broken_image).
                        transform(new CircleTransform()).
                        into(holder.mImageViewAvatar);
        }

        private void authStartActivity() {
            Intent intent = new Intent(mContext, AuthActivity.class);
            mContext.startActivity(intent);
        }

        private void likeComment(String comment_id) {
            limeButtonView(true, false);

            LikeMgr.getInstance().likeComment(comment_id, mProject, new OnLikeCommentListener() {
                @Override
                public void OnResponse(int errCode, LikeCommentResponse response) {
                    if (errCode == HttpStatus.SC_UNAUTHORIZED) {
                        authStartActivity();
                        limeButtonView(true, true);
                        return;
                    }

                    if (response == null) {
                        Snackbar.make(mButtonLike, R.string.like_comment_error, Snackbar.LENGTH_LONG).show();
                        limeButtonView(true, true);
                        return;
                    }

                    if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                        Snackbar.make(mButtonLike, response.getErrors(), Snackbar.LENGTH_LONG).show();
                        limeButtonView(true, true);
                        return;
                    }

                    limeButtonView(false, true);
                    mTextViewLikeCount.setText(response.getLikes());

                    Comment comment = mResource.get((Integer) mViewGroupLikeGroup.getTag());
                    comment.getLikes().setIsLike(true);
                    comment.getLikes().setCount(Integer.valueOf(mTextViewLikeCount.getText().toString()));
                    if (comment.getLikes().getCount() > 0)
                        mViewGroupLikeGroup.setVisibility(View.VISIBLE);
                }
            });
        }

        private void deslikeComment(String comment_id) {
            limeButtonView(false, false);

            LikeMgr.getInstance().deslikeComment(comment_id, mProject, new OnLikeCommentListener() {
                @Override
                public void OnResponse(int errCode, LikeCommentResponse response) {
                    if (errCode == HttpStatus.SC_UNAUTHORIZED) {
                        authStartActivity();
                        limeButtonView(false, true);
                        return;
                    }

                    if (response == null) {
                        Snackbar.make(mButtonLike, R.string.like_comment_error, Snackbar.LENGTH_LONG).show();
                        limeButtonView(false, true);
                        return;
                    }

                    if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                        Snackbar.make(mButtonLike, response.getErrors(), Snackbar.LENGTH_LONG).show();
                        limeButtonView(false, true);
                        return;
                    }

                    limeButtonView(true, true);
                    mTextViewLikeCount.setText(response.getLikes());

                    Comment comment = mResource.get((Integer) mViewGroupLikeGroup.getTag());
                    comment.getLikes().setIsLike(false);
                    comment.getLikes().setCount(Integer.valueOf(mTextViewLikeCount.getText().toString()));
                    if (comment.getLikes().getCount() == 0)
                        mViewGroupLikeGroup.setVisibility(View.INVISIBLE);
                }
            });
        }

        private void limeButtonView(boolean like, boolean enable) {
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

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == VIEW_TYPE_COMMENT_TOP)
            ((ViewCommentTopHolder) holder).bindView((ViewCommentTopHolder) holder, position);
        else
            ((ViewCommentHolder) holder).bindView((ViewCommentHolder) holder, position);
    }

    public ArrayList<Comment> getResource() {
        return mResource;
    }

    @Override
    public int getItemCount() {
        return mResource == null ? 0 : mResource.size();
    }
}
