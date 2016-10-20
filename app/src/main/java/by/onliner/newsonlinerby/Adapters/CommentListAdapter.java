package by.onliner.newsonlinerby.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.Comment;

/**
 * Created by Mi Air on 20.10.2016.
 */

public class CommentListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater lInflater;
    ArrayList<Comment> objects;

    public CommentListAdapter(Context context, ArrayList<Comment> comments) {
        mContext = context;
        objects = comments;
        lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = lInflater.inflate(R.layout.l_item_comment, parent, false);

        Comment comment = getComment(position);

        ((TextView)view.findViewById(R.id.tv_comment_author)).setText(comment.getAuthor());
        ((TextView)view.findViewById(R.id.tv_comment_date)).setText(comment.getDate());
        ((TextView)view.findViewById(R.id.tv_comment_likes_count)).setText(comment.getLikes().toString());
        ((TextView)view.findViewById(R.id.tv_comment_text)).setText(comment.getText());

        /*Picasso.with(mContext).
                load(comment.getAvatarURL()).
                error(R.drawable.ic_broken_image).
                into(((ImageView)view.findViewById(R.id.img_avatar)));
*/
        return view;
    }

    Comment getComment(int position) {
        return ((Comment) getItem(position));
    }
}