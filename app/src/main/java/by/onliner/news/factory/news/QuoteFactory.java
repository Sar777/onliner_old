package by.onliner.news.factory.news;

import org.jsoup.nodes.Element;

import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.QuoteViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

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
