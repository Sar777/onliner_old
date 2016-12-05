package by.onliner.news.Structures.News.ViewsObjects;

import android.os.Parcel;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class YoutubeViewObject extends ViewObject {
    private String mVideoId;

    public YoutubeViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_YOUTUBE_PLAYER);

        mVideoId = in.readString();
    }

    public YoutubeViewObject(String videoId) {
        super(ViewNewsType.TYPE_VIEW_YOUTUBE_PLAYER);

        mVideoId = videoId;
    }

    public String getVideoId() {
        return mVideoId;
    }

    @Override
    public boolean isValid() {
        return !mVideoId.isEmpty();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeString(mVideoId);
    }
}
