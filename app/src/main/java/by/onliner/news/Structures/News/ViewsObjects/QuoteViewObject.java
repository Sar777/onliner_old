package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class QuoteViewObject extends ViewObject {
    private String mText;

    public QuoteViewObject(String text) {
        super(ViewNewsType.TYPE_VIEW_QUOTE);
        mText = text;
    }

    public String getText() {
        return mText;
    }

    @Override
    public boolean isValid() {
        return !mText.isEmpty();
    }
}
