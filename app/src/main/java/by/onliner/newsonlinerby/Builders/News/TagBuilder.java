package by.onliner.newsonlinerby.Builders.News;

import android.view.View;

import by.onliner.newsonlinerby.Builders.IBuilder;

/**
 * Created by Mi Air on 16.10.2016.
 */

public class TagBuilder implements IBuilder<View, String> {
    private String[] tagNames;

    public TagBuilder(String... tags) {
        tagNames = new String[tags.length];
        for (int i = 0; i < tags.length; ++i)
            this.tagNames[i] = tags[i];
    }

    @Override
    public String build(View data) {

        return null;
    }
}
