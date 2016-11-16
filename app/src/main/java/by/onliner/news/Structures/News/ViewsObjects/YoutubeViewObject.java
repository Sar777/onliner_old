package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class YoutubeViewObject extends ViewObject {
    private String mVideoId;

    public YoutubeViewObject(String videoId) {
        super(ViewNewsType.TYPE_VIEW_YOUTUBE_PLAYER);

        mVideoId = videoId;
    }

    public String getVideoId() {
        return mVideoId;
    }
}
