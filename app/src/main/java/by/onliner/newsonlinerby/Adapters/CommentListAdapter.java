package by.onliner.newsonlinerby.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builder.Comment.CommentQuoteBuilder;
import by.onliner.newsonlinerby.Common.Common;
import by.onliner.newsonlinerby.Listeners.LikeCommentListener;
import by.onliner.newsonlinerby.Managers.LikeMgr;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.Comments.Comment;
import by.onliner.newsonlinerby.Transforms.CircleTransform;


/**
 * Адаптере для отображения комментариев к новости
 */
public class CommentListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Comment> mObjects;
    private String mProject;

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
        if (view == null)
            view = mInflater.inflate(R.layout.l_item_comment, parent, false);

        Comment comment = getComment(position);

        ((TextView)view.findViewById(R.id.tv_comment_author)).setText(comment.getAuthor().getName());
        ((TextView)view.findViewById(R.id.tv_comment_date)).setText(comment.getDate());

        if (comment.getLikes().getCount() > 0) {
            ((TextView) view.findViewById(R.id.tv_comment_likes_count)).setText(comment.getLikes().getCount().toString());
            if (comment.getLikes().isBest())
                ((ImageView) view.findViewById(R.id.img_like_type)).setImageDrawable(ContextCompat.getDrawable(App.getContext(), R.drawable.i_best_like));
            else
                ((ImageView) view.findViewById(R.id.img_like_type)).setImageDrawable(ContextCompat.getDrawable(App.getContext(), R.drawable.i_default_like));
        }
        else
            view.findViewById(R.id.l_like_group).setVisibility(View.GONE);

        ((ViewGroup) view.findViewById(R.id.l_comment_text)).removeAllViews();

        TextView commentTextView = new TextView(App.getContext());
        commentTextView.setTextColor(Color.BLACK);
        commentTextView.setText(Common.fromHtml(comment.getText()));
        commentTextView.setPadding(30, 40, 30, 0);

        // Формирование цитаты в комментарии
        if (comment.getQuote() != null) {
            View quoteView = new CommentQuoteBuilder().build(comment.getQuote());
            if (quoteView != null) {
                ((ViewGroup) view.findViewById(R.id.l_comment_text)).addView(quoteView, 0);
                commentTextView.setPadding(30, 0, 30, 0);
            }
        }

        ((ViewGroup) view.findViewById(R.id.l_comment_text)).addView(commentTextView);

        Button likeButton = (Button)view.findViewById(R.id.bt_like_comment);
        likeButton.setTag(comment.getId());
        likeButton.setOnClickListener(this);

        if (!comment.getAvatarURL().isEmpty())
            Picasso.with(mContext).
                   load(comment.getAvatarURL()).
                   error(R.drawable.ic_broken_image).
                   transform(new CircleTransform()).
                   into(((ImageView)view.findViewById(R.id.img_avatar)));

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