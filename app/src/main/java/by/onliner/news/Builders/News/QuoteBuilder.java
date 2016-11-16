package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.QuoteViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Тег <blockquote> </blockquote>
 */
public class QuoteBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        if (element.className().indexOf("instagram-media") != -1)
            return null;

        return new QuoteViewObject(element.text());
    }
}
