package by.onliner.news.Structures.News.ViewsObjects;

import java.util.ArrayList;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public class VoteViewObject extends ViewObject {
    private String mTitle;
    private String mState;
    private ArrayList<String> mOptions;

    public VoteViewObject(String title, String state, ArrayList<String> options) {
        super(ViewNewsType.TYPE_VIEW_VOTE);
        this.mTitle = title;
        this.mState = state;
        this.mOptions = options;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getState() {
        return mState;
    }

    public ArrayList<String> getOptions() {
        return mOptions;
    }

    @Override
    public boolean isValid() {
        return !mTitle.isEmpty() && !mOptions.isEmpty();
    }
}
