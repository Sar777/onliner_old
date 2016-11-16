package by.onliner.news.Structures.News.ViewsObjects;

import by.onliner.news.Enums.ViewNewsType;

/**
 * speech div блок
 */
public class SpeechViewObject extends ViewObject {
    private String mText;

    public SpeechViewObject(String text) {
        super(ViewNewsType.TYPE_VIEW_SPEECH);
        mText = text;
    }

    public String getText() {
        return mText;
    }
}
