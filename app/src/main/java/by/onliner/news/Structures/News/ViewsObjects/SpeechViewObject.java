package by.onliner.news.Structures.News.ViewsObjects;

import android.os.Parcel;

import by.onliner.news.Enums.ViewNewsType;

/**
 * speech div блок
 */
public class SpeechViewObject extends ViewObject {
    private String mText;

    public SpeechViewObject(Parcel in) {
        super(ViewNewsType.TYPE_VIEW_SPEECH);

        mText = in.readString();
    }

    public SpeechViewObject(String text) {
        super(ViewNewsType.TYPE_VIEW_SPEECH);

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
