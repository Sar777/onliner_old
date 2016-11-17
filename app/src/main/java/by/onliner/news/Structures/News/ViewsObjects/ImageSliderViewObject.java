package by.onliner.news.Structures.News.ViewsObjects;

import java.util.ArrayList;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class ImageSliderViewObject extends ViewObject {
    private ArrayList<String> mImageURLs;
    private String mDescription;

    public ImageSliderViewObject(ArrayList<String> imageUrls, String description) {
        super(ViewNewsType.TYPE_VIEW_IMAGE_SLIDER);
        this.mImageURLs =  imageUrls;
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
}
