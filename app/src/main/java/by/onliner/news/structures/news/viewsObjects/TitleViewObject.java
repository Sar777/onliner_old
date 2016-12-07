package by.onliner.news.structures.news.viewsObjects;

import android.os.Parcel;

import by.onliner.news.enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class TitleViewObject extends ViewObject {
    private String mText;

    public TitleViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_TITLE);

        mText = in.readString();
    }

    public TitleViewObject(String text) {
        super(ViewNewsType.TYPE_VIEW_TITLE);

        mText = text;
    }

    public String getText() {
        return mText;
    }

    @Override
    public boolean isValid() {
        return !mText.isEmpty();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeString(mText);
    }
}
