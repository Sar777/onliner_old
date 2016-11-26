package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;
import by.onliner.news.Structures.News.NewsPreview;

public class PreviewViewObject extends ViewObject {

    private String mTitle;
    private Integer mViews;
    private Integer mComments;
    private String mDate;
    private String mImageUrl;

    public PreviewViewObject(String title, Integer views, Integer comments, String date, String imageUrl) {
        super(ViewNewsType.TYPE_VIEW_HEADER);

        this.mTitle = title;
        this.mViews = views;
        this.mComments = comments;
        this.mDate = date;
        this.mImageUrl = imageUrl;
    }

    public PreviewViewObject(NewsPreview preview) {
        super(ViewNewsType.TYPE_VIEW_HEADER);

        this.mTitle = preview.getTitle();
        this.mViews = preview.getView();
        this.mComments = preview.getComments();
        this.mDate = preview.getPostDate();
        this.mImageUrl = preview.getImage();
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

    @Override
    public boolean isValid() {
        return !mTitle.isEmpty() && !mImageUrl.isEmpty();
    }
}
