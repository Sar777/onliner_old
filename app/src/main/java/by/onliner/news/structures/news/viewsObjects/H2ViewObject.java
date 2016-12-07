package by.onliner.news.structures.news.viewsObjects;

import android.os.Parcel;

import by.onliner.news.enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class H2ViewObject extends ViewObject {
    private String mText;

    public H2ViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_H2);

        mText = in.readString();
    }

    public H2ViewObject(String text) {
        super(ViewNewsType.TYPE_VIEW_H2);

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
