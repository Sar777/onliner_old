package by.onliner.news.Structures.News.ViewsObjects;

import android.os.Parcel;
import android.os.Parcelable;

import by.onliner.news.Enums.ViewNewsType;
import by.onliner.news.Structures.News.NewsPreview;

public class PreviewViewObject extends ViewObject {

    private String mTitle;
    private Integer mViews;
    private Integer mComments;
    private String mDate;
    private String mImageUrl;

    public PreviewViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_PREVIEW);

        mTitle = in.readString();
        mViews = in.readInt();
        mComments = in.readInt();
        mDate = in.readString();
        mImageUrl = in.readString();
    }

    public PreviewViewObject(String title, Integer views, Integer comments, String date, String imageUrl) {
        super(ViewNewsType.TYPE_VIEW_PREVIEW);

        this.mTitle = title;
        this.mViews = views;
        this.mComments = comments;
        this.mDate = date;
        this.mImageUrl = imageUrl;
    }

    public PreviewViewObject(NewsPreview preview) {
        super(ViewNewsType.TYPE_VIEW_PREVIEW);

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

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeString(mTitle);
        out.writeInt(mViews);
        out.writeInt(mComments);
        out.writeString(mDate);
        out.writeString(mImageUrl);
    }

    public static final Parcelable.Creator<PreviewViewObject> CREATOR = new Parcelable.Creator<PreviewViewObject>() {
        public PreviewViewObject createFromParcel(Parcel in) {
            return new PreviewViewObject(in);
        }

        public PreviewViewObject[] newArray(int size) {
            return new PreviewViewObject[size];
        }
    };
}
