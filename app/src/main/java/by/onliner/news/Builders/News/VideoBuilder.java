package by.onliner.news.Builders.News;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.youtube.player.YouTubeThumbnailView;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Common.Common;
import by.onliner.news.Common.Config;
import by.onliner.news.Listeners.YoutubeThumbnailListener;
import by.onliner.news.R;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class VideoBuilder implements IBuilder<Element, ViewObject> {
    private Activity mActivity;

    public VideoBuilder() {
        this.mActivity = null;
    }

    @Override
    public ViewObject build(Element element) {
        Element frameElement = element.getElementsByTag("iframe").first();
        if (frameElement == null)
            return null;

        final String youtubeVideoId = Common.getYoutubeVideoId(frameElement.attr("src"));
        if (youtubeVideoId.isEmpty())
            return null;

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.layout_youtube_player, null);
        final YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_thumbnail);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                youTubeThumbnailView.initialize(Config.DEVELOPER_YOUTUBE_KEY, new YoutubeThumbnailListener(mActivity, view, youtubeVideoId));
            }
        }, 2000);

        return null;
    }
}
