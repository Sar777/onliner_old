package by.onliner.news.Structures.News.ViewsObjects;

import android.text.Spanned;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class SpanViewObject extends ViewObject {
    private Spanned mText;

    public SpanViewObject(Spanned text) {
        super(ViewNewsType.TYPE_VIEW_SPAN);
        mText = text;
    }

    public Spanned getText() {
        return mText;
    }

    public void setText(Spanned text) {
        mText = text;
    }
}
