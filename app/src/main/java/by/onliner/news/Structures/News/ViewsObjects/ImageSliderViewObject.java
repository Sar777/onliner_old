package by.onliner.news.Structures.News.ViewsObjects;

import android.os.Parcel;

import java.util.ArrayList;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class ImageSliderViewObject extends ViewObject {
    private ArrayList<String> mImageURLs;
    private String mDescription;

    public ImageSliderViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_IMAGE_SLIDER);

        in.readStringList(mImageURLs);
        mDescription = in.readString();
    }

    public ImageSliderViewObject(ArrayList<String> imageUrls, String description) {
        super(ViewNewsType.TYPE_VIEW_IMAGE_SLIDER);

        this.mImageURLs = imageUrls;
        this.mDescription = description;
    }

    public ArrayList<String> getImageURLs() {
        return mImageURLs;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public boolean isValid() {
        return !mImageURLs.isEmpty();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeStringList(mImageURLs);
        out.writeString(mDescription);
    }
}
