package by.onliner.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.news.activity.ViewNewsActivity;
import by.onliner.news.Fragments.tabs.TabBase;
import by.onliner.news.listeners.OnLoadMoreListener;
import by.onliner.news.managers.NewsMgr;
import by.onliner.news.R;
import by.onliner.news.structures.news.News;
import by.onliner.news.structures.news.NewsPreview;

/**
 * Created by orion on 12.11.16.
 */

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<News> mResource;
    private Context mContext;
    private RecyclerView mRecyclerView;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean mIsLoading;
    private int mVisibleThreshold = 5;
    private int mLastVisibleItem, mTotalItemCount;

    public NewsListAdapter(Context context, String project, RecyclerView recyclerView) {
        this.mContext = context;
        this.mResource = NewsMgr.getInstance().getNewsList(project);
        this.mRecyclerView = recyclerView;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }

                    mIsLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mResource.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_item, parent, false);

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            if (linearLayoutManager.getOrientation() == LinearLayout.HORIZONTAL) {
                CardView cardView = (CardView) view.findViewById(R.id.cv_preview);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 10, 0, 10);
                cardView.setLayoutParams(params);
            }
            return new ViewDataHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    private class ViewDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Views
        private TextView mTextViewTitle;
        private TextView mTextViewComments;
        private TextView mTextViewViews;
        private TextView mTextViewUpd;
        private TextView mTextViewPhotos;
        private ImageView mImageviewPreview;
        private ImageView mImageviewVideo;
        private ViewGroup mViewGroupPhotos;

        ViewDataHolder(View itemView)  {
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
            intent.putExtra(TabBase.INTENT_TITLE_TAG, item.getPreview().getTitle());
            mContext.startActivity(intent);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar)itemView.findViewById(R.id.pb_loading_more);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // Данные
        if (holder instanceof ViewDataHolder) {
            bindViewDataItem(((ViewDataHolder)holder), position);
        }
        // Прогресс
        else if (holder instanceof LoadingViewHolder) {
            bindViewLoading((LoadingViewHolder)holder, position);
        }
    }

    private void bindViewDataItem(final ViewDataHolder holder, final int position) {
        News news = mResource.get(position);
        if (news == null)
            return;

        NewsPreview header = news.getPreview();

        Picasso.with(mContext).
                load(header.getImage()).
                error(R.drawable.ic_broken_image).
                into(holder.mImageviewPreview);

        holder.mTextViewTitle.setText(header.getTitle());
        holder.mTextViewComments.setText(header.getComments().toString());
        holder.mTextViewViews.setText(header.getView().toString());

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

    private void bindViewLoading(final LoadingViewHolder holder, final int position) {
        holder.mProgressBar.setIndeterminate(true);
    }

    public ArrayList<News> getResource() {
        return mResource;
    }

    @Override
    public int getItemCount() {
        return mResource == null ? 0 : mResource.size();
    }

    public void setLoaded() {
        mIsLoading = false;
    }
}
