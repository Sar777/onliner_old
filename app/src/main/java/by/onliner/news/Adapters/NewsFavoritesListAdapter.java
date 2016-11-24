package by.onliner.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.news.Activity.ViewNewsActivity;
import by.onliner.news.Fragments.Tabs.TabBase;
import by.onliner.news.Managers.NewsMgr;
import by.onliner.news.R;
import by.onliner.news.Structures.News.News;
import by.onliner.news.Structures.News.NewsHeader;

/**
 * Created by orion on 12.11.16.
 */

public class NewsFavoritesListAdapter extends RecyclerView.Adapter<NewsFavoritesListAdapter.ViewItemHolder> {
    private ArrayList<News> mResource;
    private Context mContext;

    public NewsFavoritesListAdapter(Context context, ArrayList<String> urls) {
        this.mContext = context;
        this.mResource = NewsMgr.getInstance().getNewsListByUrls(urls);
    }

    @Override
    public ViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item, parent, false);
        return new ViewItemHolder(view);
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Views
        private TextView mTextViewTitle;
        private TextView mTextViewComments;
        private TextView mTextViewViews;
        private TextView mTextViewUpd;
        private TextView mTextViewPhotos;
        private ImageView mImageviewPreview;
        private ImageView mImageviewVideo;
        private ViewGroup mViewGroupPhotos;

        public ViewItemHolder(View itemView)  {
            super(itemView);

            mImageviewPreview = (ImageView)itemView.findViewById(R.id.i_preview_image);
            mImageviewVideo = (ImageView)itemView.findViewById(R.id.i_preview_video);
            mTextViewTitle = (TextView)itemView.findViewById(R.id.tv_preview_title);
            mTextViewComments = (TextView)itemView.findViewById(R.id.tv_preview_comments);
            mTextViewViews = (TextView)itemView.findViewById(R.id.tv_preview_views);
            mTextViewUpd = (TextView)itemView.findViewById(R.id.tv_preview_upd_status);
            mTextViewPhotos = (TextView)itemView.findViewById(R.id.tv_preview_photos);
            mViewGroupPhotos = (ViewGroup)itemView.findViewById(R.id.l_preview_photos);

            // Listeners
            mImageviewPreview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            News item = mResource.get(getAdapterPosition());
            Intent intent = new Intent(mContext, ViewNewsActivity.class);
            intent.putExtra(TabBase.INTENT_URL_TAG, item.getAttributes().getUrl());
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(final ViewItemHolder holder, final int position) {
        News news = mResource.get(position);
        if (news == null)
            return;

        NewsHeader header = news.getHeader();
        Picasso.with(mContext).
                load(header.getImage()).
                error(R.drawable.ic_broken_image).
                into(holder.mImageviewPreview);

        holder.mTextViewTitle.setText(header.getTitle());
        holder.mTextViewComments.setText(header.getComments().toString());
        holder.mTextViewViews.setText(header.getViews().toString());

        // Attributes
        if (header.getAttributes().getUpd())
            holder.mTextViewUpd.setVisibility(View.VISIBLE);
        else
            holder.mTextViewUpd.setVisibility(View.GONE);

        if (header.getAttributes().getPhotos() > 0) {
            holder.mViewGroupPhotos.setVisibility(View.VISIBLE);
            holder.mTextViewPhotos.setText(header.getAttributes().getPhotos().toString());
        }
        else
            holder.mViewGroupPhotos.setVisibility(View.GONE);

        if (header.getAttributes().getCamera())
            holder.mImageviewVideo.setVisibility(View.VISIBLE);
        else
            holder.mImageviewVideo.setVisibility(View.GONE);
    }

    public ArrayList<News> getResource() {
        return mResource;
    }

    @Override
    public int getItemCount() {
        return mResource == null ? 0 : mResource.size();
    }
}
