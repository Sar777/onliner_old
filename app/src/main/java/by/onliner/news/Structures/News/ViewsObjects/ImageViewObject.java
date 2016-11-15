package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class ImageViewObject extends ViewObject {
    private String mURL;
    private String mDescription;

    public ImageViewObject(String url, String description) {
        super(ViewNewsType.TYPE_VIEW_IMAGE);
        mURL = url;
        mDescription = description;
    }

    public String getUrl() {
        return mURL;
    }

    public String getDescription() {
        return mDescription;
    }
}
