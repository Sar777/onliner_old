package by.onliner.news.Structures.News.ViewsObjects;

import android.text.Spanned;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class ULViewObject extends ViewObject {
    private Spanned mText;

    public ULViewObject(Spanned text) {
        super(ViewNewsType.TYPE_VIEW_UL);
        mText = text;
    }

    public Spanned getText() {
        return mText;
    }
}
