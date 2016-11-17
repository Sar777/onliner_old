package by.onliner.news.Factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.ViewsObjects.QuoteViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Тег <blockquote> </blockquote>
 */
public class QuoteFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        if (element.className().indexOf("instagram-media") != -1)
            return null;

        return new QuoteViewObject(element.text());
    }
}
