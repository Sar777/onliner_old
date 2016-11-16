package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class H2ViewObject extends ViewObject {
    private String mText;

    public H2ViewObject(String text) {
        super(ViewNewsType.TYPE_VIEW_H2);
        mText = text;
    }

    public String getText() {
        return mText;
    }
}
