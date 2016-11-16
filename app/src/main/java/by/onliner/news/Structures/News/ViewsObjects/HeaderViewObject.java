package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;
import by.onliner.news.Structures.News.NewsHeader;

public class HeaderViewObject extends ViewObject {

    private String mTitle;
    private Integer mViews;
    private Integer mComments;
    private String mDate;
    private String mImageUrl;

    public HeaderViewObject(String title, Integer views, Integer comments, String date, String imageUrl) {
        super(ViewNewsType.TYPE_VIEW_HEADER);

        this.mTitle = title;
        this.mViews = views;
        this.mComments = comments;
        this.mDate = date;
        this.mImageUrl = imageUrl;
    }

    public HeaderViewObject(NewsHeader header) {
        super(ViewNewsType.TYPE_VIEW_HEADER);

        this.mTitle = header.getTitle();
        this.mViews = header.getViews();
        this.mComments = header.getComments();
        this.mDate = header.getPostDate();
        this.mImageUrl = header.getImage();
    }

    public String getTitle() {
        return mTitle;
    }

    public Integer getViews() {
        return mViews;
    }

    public Integer getComments() {
        return mComments;
    }

    public String getDate() {
        return mDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
