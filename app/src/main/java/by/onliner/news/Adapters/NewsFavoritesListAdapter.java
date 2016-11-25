package by.onliner.news.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.news.Activity.ViewNewsActivity;
import by.onliner.news.Fragments.Tabs.TabBase;
import by.onliner.news.Managers.FavoritesNewsMgr;
import by.onliner.news.R;
import by.onliner.news.Structures.News.News;
import by.onliner.news.Structures.News.NewsHeader;

/**
 * Created by orion on 12.11.16.
 */

public class NewsFavoritesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<News> mResource;
    private Context mContext;

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_EMPTY = 1;

    public NewsFavoritesListAdapter(Context context) {
        this.mContext = context;
        this.mResource = FavoritesNewsMgr.getInstance().getAllFavorites();
    }

    @Override
    public int getItemViewType(int position) {
        return mResource.isEmpty() ? VIEW_TYPE_EMPTY : VIEW_TYPE_DATA;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY)
            return new ViewEmptyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_recycler_favorite_news, parent, false));

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item, parent, false);
        return new ViewItemHolder(view);
    }

    private class ViewEmptyHolder extends RecyclerView.ViewHolder {
        ViewEmptyHolder(View itemView) {
            super(itemView);
        }
    }

    private class ViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Views
        private TextView mTextViewTitle;
        private TextView mTextViewComments;
        private TextView mTextViewViews;
        private TextView mTextViewUpd;
        private TextView mTextViewPhotos;
        private ImageView mImageviewPreview;
        private ImageView mImageviewVideo;
        private ViewGroup mViewGroupPhotos;

        ViewItemHolder(View itemView)  {
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
            intent.putExtra(TabBase.INTENT_PROJECT_TAG, item.getAttributes().getProject());
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewEmptyHolder)
            return;

        ViewItemHolder viewItemHolder = (ViewItemHolder)holder;

        News news = mResource.get(position);
        if (news == null)
            return;

        NewsHeader header = news.getHeader();
        Picasso.with(mContext).
                load(header.getImage()).
                error(R.drawable.ic_broken_image).
                into(viewItemHolder.mImageviewPreview);

        viewItemHolder.mTextViewTitle.setText(header.getTitle());
        viewItemHolder.mTextViewComments.setText(header.getComments().toString());
        viewItemHolder.mTextViewViews.setText(header.getViews().toString());

        // Attributes
        if (header.getAttributes().getUpd())
            viewItemHolder.mTextViewUpd.setVisibility(View.VISIBLE);
        else
            viewItemHolder.mTextViewUpd.setVisibility(View.GONE);

        if (header.getAttributes().getPhotos() > 0) {
            viewItemHolder.mViewGroupPhotos.setVisibility(View.VISIBLE);
            viewItemHolder.mTextViewPhotos.setText(header.getAttributes().getPhotos().toString());
        }
        else
            viewItemHolder.mViewGroupPhotos.setVisibility(View.GONE);

        if (header.getAttributes().getCamera())
            viewItemHolder.mImageviewVideo.setVisibility(View.VISIBLE);
        else
            viewItemHolder.mImageviewVideo.setVisibility(View.GONE);
    }

    public ArrayList<News> getResource() {
        return mResource;
    }

    @Override
    public int getItemCount() {
        return mResource == null ? 0 : (mResource.isEmpty() ? 1 : mResource.size());
    }
}
