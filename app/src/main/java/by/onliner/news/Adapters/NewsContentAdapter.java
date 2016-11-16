package by.onliner.news.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import by.onliner.news.App;
import by.onliner.news.Common.Config;
import by.onliner.news.CustomViews.QuoteTextView;
import by.onliner.news.Enums.ViewNewsType;
import by.onliner.news.Listeners.FullScreenImageListener;
import by.onliner.news.Listeners.YoutubeThumbnailListener;
import by.onliner.news.R;
import by.onliner.news.Structures.News.ViewsObjects.ImageViewObject;
import by.onliner.news.Structures.News.ViewsObjects.QuoteViewObject;
import by.onliner.news.Structures.News.ViewsObjects.SpanViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;
import by.onliner.news.Structures.News.ViewsObjects.YoutubeViewObject;

import static by.onliner.news.Enums.ViewNewsType.values;

/**
 * Адаптер для RecyclerView отвечающий за просмотр содержимого новости
 */
public class NewsContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private ArrayList<ViewObject> mResource;

    public NewsContentAdapter(Activity activity, ArrayList<ViewObject> mResource) {
        this.mActivity = activity;
        this.mResource = mResource;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewNewsType viewNewsType = values()[viewType];
        switch (viewNewsType) {
            case TYPE_VIEW_SPAN:
                return new NewsContentAdapter.SpanViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_view_news_span, parent, false));
            case TYPE_VIEW_QUOTE:
                return new NewsContentAdapter.QuoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_view_news_quote, parent, false));
            case TYPE_VIEW_HR:
                return new NewsContentAdapter.HRViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_view_news_hr, parent, false));
            case TYPE_VIEW_IMAGE:
                return new NewsContentAdapter.ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_view_news_image, parent, false));
            case TYPE_VIEW_YOUTUBE_PLAYER:
                return new NewsContentAdapter.YoutubeVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_view_news_youtube_player, parent, false));
            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewNewsType viewNewsType = values()[holder.getItemViewType()];
        switch (viewNewsType) {
            case TYPE_VIEW_SPAN:
                ((SpanViewHolder)holder).bind(holder, position);
                break;
            case TYPE_VIEW_QUOTE:
                ((QuoteViewHolder)holder).bind(holder, position);
                break;
            case TYPE_VIEW_IMAGE:
                ((ImageViewHolder)holder).bind(holder, position);
                break;
            case TYPE_VIEW_YOUTUBE_PLAYER:
                ((YoutubeVideoViewHolder)holder).bind(holder, position);
                break;
            case TYPE_VIEW_HR:
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mResource.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mResource.get(position).getType().ordinal();
    }

    private class SpanViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public SpanViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.tv_content_news_spawn);
        }

        public void bind(final RecyclerView.ViewHolder holder, final int position) {
            final SpanViewObject spanView = (SpanViewObject)mResource.get(position);
            mTextView.setText(spanView.getText());
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextViewDescription;
        private ProgressBar mProgressBarRLoading;
        private ViewGroup mLayoutRepeatGroup;
        private Button mButtonLoadingRepeat;

        private String mUrl;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.img_view_news);
            mTextViewDescription = (TextView) itemView.findViewById(R.id.tv_view_news_img_description);
            mProgressBarRLoading = (ProgressBar) itemView.findViewById(R.id.pb_view_news_loading);
            mLayoutRepeatGroup = (ViewGroup) itemView.findViewById(R.id.l_view_news_repeat);
            mButtonLoadingRepeat = (Button) itemView.findViewById(R.id.bt_view_news_repeat_loading);
        }

        public void bind(final RecyclerView.ViewHolder holder, final int position) {
            final ImageViewObject imageViewObject = (ImageViewObject) mResource.get(position);

            mUrl = imageViewObject.getUrl();

            loadingImage();

            if (!imageViewObject.getDescription().isEmpty()) {
                mTextViewDescription.setVisibility(View.VISIBLE);
                mTextViewDescription.setText(imageViewObject.getDescription());
            }
            else
                mTextViewDescription.setVisibility(View.GONE);

            mImageView.setOnClickListener(this);
            mButtonLoadingRepeat.setOnClickListener(this);
        }

        private void loadingImage() {
            mLayoutRepeatGroup.setVisibility(View.GONE);
            mProgressBarRLoading.setVisibility(View.VISIBLE);

            Picasso.with(App.getContext()).
                    load(mUrl).
                    resize(0, 300).
                    into(mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            mProgressBarRLoading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            mLayoutRepeatGroup.setVisibility(View.VISIBLE);
                            mProgressBarRLoading.setVisibility(View.GONE);
                        }
                    });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_view_news: {
                    new FullScreenImageListener(mActivity, new ArrayList<>(Arrays.asList(mUrl)));
                    break;
                }
                case R.id.bt_view_news_repeat_loading: {
                    loadingImage();
                    break;
                }
                default:
                    break;
            }
        }
    }

    private class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        public ImageSliderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class QuoteViewHolder extends RecyclerView.ViewHolder {
        private QuoteTextView mQuoteTextView;

        public QuoteViewHolder(View itemView) {
            super(itemView);

            mQuoteTextView = (QuoteTextView) itemView.findViewById(R.id.tv_content_news_quote);
        }

        public void bind(final RecyclerView.ViewHolder holder, final int position) {
            final QuoteViewObject quoteViewObject = (QuoteViewObject) mResource.get(position);
            mQuoteTextView.setText(quoteViewObject.getText());
        }
    }

    private class YoutubeVideoViewHolder extends RecyclerView.ViewHolder {
        private YouTubeThumbnailView mYouTubeThumbnailView;
        private ImageView mImageViewPlay;

        public YoutubeVideoViewHolder(View itemView) {
            super(itemView);

            mYouTubeThumbnailView = (YouTubeThumbnailView)itemView.findViewById(R.id.youtube_view_news_thumbnail);
            mImageViewPlay = (ImageView)itemView.findViewById(R.id.bt_view_news_youtube_player);
        }

        public void bind(final RecyclerView.ViewHolder holder, final int position) {
            final YoutubeViewObject youtubeViewObject = (YoutubeViewObject) mResource.get(position);
            mYouTubeThumbnailView.initialize(Config.DEVELOPER_YOUTUBE_KEY, new YoutubeThumbnailListener(mActivity, mImageViewPlay, youtubeViewObject.getVideoId()));
        }
    }

    private class HRViewHolder extends RecyclerView.ViewHolder {
        public HRViewHolder(View itemView) {
            super(itemView);
        }
    }
}
