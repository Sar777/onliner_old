package by.onliner.newsonlinerby.Builders.News;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubeThumbnailView;

import org.jsoup.nodes.Element;

import by.onliner.newsonlinerby.Builders.IBuilder;
import by.onliner.newsonlinerby.Common.Config;
import by.onliner.newsonlinerby.Listeners.YoutubeThumbnailListener;
import by.onliner.newsonlinerby.R;

/**
 * Добавление плеера Youtube на экран
 */
public class VideoBuilder implements IBuilder<LinearLayout, LinearLayout>  {
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

        String srcUrl = element.attr("src");
        if (srcUrl.isEmpty())
            return layout;

        final String youtubeVideoId = srcUrl.split("/")[srcUrl.split("/").length - 1]; // Кусок с айди видео
        if (youtubeVideoId.isEmpty())
            return layout;

        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.layout_youtube_player, null);
        final YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_thumbnail);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                youTubeThumbnailView.initialize(Config.DEVELOPER_YOUTUBE_KEY, new YoutubeThumbnailListener(mActivity, view, youtubeVideoId));
            }
        });

        layout.addView(view);
        return layout;
    }
}
