package by.onliner.newsonlinerby.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.Comments.Comment;
import by.onliner.newsonlinerby.Transforms.CircleTransform;

/**
 * Created by Mi Air on 20.10.2016.
 */

public class CommentListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<Comment> mObjects;

    public CommentListAdapter(Context context, ArrayList<Comment> comments) {
        mContext = context;
        mObjects = comments;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        ((TextView)view.findViewById(R.id.tv_comment_text)).setText(comment.getText());

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
}