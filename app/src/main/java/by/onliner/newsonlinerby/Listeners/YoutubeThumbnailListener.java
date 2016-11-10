package by.onliner.newsonlinerby.Listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import by.onliner.newsonlinerby.Common.Config;
import by.onliner.newsonlinerby.R;

/**
 * Слушатель для Youtube
 */
public class YoutubeThumbnailListener implements YouTubeThumbnailView.OnInitializedListener {
    private Activity mActivity;
    private View mView;
    private String mYoutubeVideoId;

    public YoutubeThumbnailListener(Activity activity, View view, String youtubeViewId) {
        this.mActivity = activity;
        this.mView = view;
        this.mYoutubeVideoId = youtubeViewId;
    }
    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        youTubeThumbnailLoader.setVideo(mYoutubeVideoId);
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                mView.findViewById(R.id.relative_over_youtube_thumbnail).setVisibility(View.VISIBLE);

                mView.findViewById(R.id.btn_youtube_player).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(mActivity, Config.DEVELOPER_YOUTUBE_KEY, mYoutubeVideoId);
                        mActivity.startActivity(intent);
                    }
                });
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) { }
        });
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {}
}
