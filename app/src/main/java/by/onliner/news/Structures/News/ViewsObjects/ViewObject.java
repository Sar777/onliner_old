package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public abstract class ViewObject {
    ViewNewsType mType;

    public ViewObject(ViewNewsType type) {
        this.mType = type;
    }

    public ViewNewsType getType() {
        return mType;
    }

    public abstract boolean isValid();
}
