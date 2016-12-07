package by.onliner.news.structures.news.viewsObjects;

import android.os.Parcel;
import android.text.Spanned;

import by.onliner.news.enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class SpanViewObject extends ViewObject {
    private Spanned mText;

    public SpanViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_SPAN);

        mText = (Spanned) in.readValue(Spanned.class.getClassLoader());
    }

    public SpanViewObject(Spanned text) {
        super(ViewNewsType.TYPE_VIEW_SPAN);

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
