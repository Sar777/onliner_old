package by.onliner.news.Builders.News;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubeThumbnailView;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Common.Common;
import by.onliner.news.Common.Config;
import by.onliner.news.Listeners.YoutubeThumbnailListener;
import by.onliner.news.R;

/**
 * Добавление плеера Youtube на экран
 */
public class VideoBuilder implements IBuilder<LinearLayout, LinearLayout> {
    private Element mElement;
    private Activity mActivity;

    public VideoBuilder(Element element, Activity activity) {
        this.mElement = element;
        this.mActivity = activity;
    }

    @Override
    public LinearLayout build(LinearLayout layout) {
        Element element = mElement.getElementsByTag("iframe").first();
        if (element == null)
            return layout;

        final String youtubeVideoId = Common.getYoutubeVideoId(element.attr("src"));
        if (youtubeVideoId.isEmpty())
            return layout;

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.layout_youtube_player, null);
        final YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_thumbnail);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                youTubeThumbnailView.initialize(Config.DEVELOPER_YOUTUBE_KEY, new YoutubeThumbnailListener(mActivity, view, youtubeVideoId));
            }
        }, 2000);

        layout.addView(view);
        return layout;
    }
}
