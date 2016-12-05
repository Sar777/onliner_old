package by.onliner.news.Structures.News.ViewsObjects;

import android.os.Parcel;
import android.text.Spanned;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class ULViewObject extends ViewObject {
    private Spanned mText;

    public ULViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_UL);

        mText = (Spanned) in.readValue(Spanned.class.getClassLoader());
    }

    public ULViewObject(Spanned text) {
        super(ViewNewsType.TYPE_VIEW_UL);
        mText = text;
    }

    public Spanned getText() {
        return mText;
    }

    @Override
    public boolean isValid() {
        return mText.length() != 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeValue(mText);
    }
}
