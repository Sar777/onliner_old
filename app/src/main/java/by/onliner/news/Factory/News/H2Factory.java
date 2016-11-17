package by.onliner.news.Factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.ViewsObjects.H2ViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Тег <h2> </h2>
 */
public class H2Factory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new H2ViewObject(element.text());
    }
}
