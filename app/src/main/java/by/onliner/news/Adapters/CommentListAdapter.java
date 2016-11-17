package by.onliner.news.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.news.App;
import by.onliner.news.Common.Common;
import by.onliner.news.Factory.Comment.CommentQuoteFactory;
import by.onliner.news.Listeners.LikeCommentListener;
import by.onliner.news.Managers.LikeMgr;
import by.onliner.news.R;
import by.onliner.news.Structures.Comments.Comment;
import by.onliner.news.Transforms.CircleTransform;

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

        if (comment.getLikes().isBest() && position == 0)
            mTextViewTopComment.setVisibility(View.VISIBLE);
        else
            mTextViewTopComment.setVisibility(View.GONE);

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
        mButtonLike.setOnClickListener(this);

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
            case R.id.bt_like_comment: {
                LikeMgr.getInstance().asyncLikeComment(view.getTag().toString(), mProject, new LikeCommentListener() {
                    @Override
                    public void OnResponse(int code, String json) {
                        Log.e("ORION", "CODE: " + code + " RESP: " + json);
                    }
                });
                break;
            }
            default:
                break;
        }
    }
}