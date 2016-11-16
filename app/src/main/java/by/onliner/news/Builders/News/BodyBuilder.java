package by.onliner.news.Builders.News;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import by.onliner.news.App;
import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.News;

/**
 * Формирование блока новости
 */
public class BodyBuilder implements IBuilder<View, View> {
    private News mContent;
    private Activity mActivity;

    public BodyBuilder(Activity activity, News content) {
        this.mActivity = activity;
        this.mContent = content;
    }

    @Override
    public View build(View layout2) {
        Document doc = Jsoup.parse(mContent.getContent());

        LinearLayout layout = new LinearLayout(App.getContext());

        for (Element element : doc.getAllElements()) {
            switch (element.tagName()) {
                case "div": {
                    // Слайдер изображений
                    if (element.className().indexOf("news-media__gallery") != -1)
                        new ImageSliderBuilder(element, mActivity).build(layout);
                    break;
                }
                default:
                    continue;
            }
        }

        return layout;
    }
}
