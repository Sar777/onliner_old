package by.onliner.news.Factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.ViewsObjects.HRViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Тег <hr> </hr>
 */
public class HRFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new HRViewObject();
    }
}
