package by.onliner.news.factory.news;

import org.jsoup.nodes.Element;

import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.H2ViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Тег <h2> </h2>
 */
public class H2Factory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new H2ViewObject(element.text());
    }
}
