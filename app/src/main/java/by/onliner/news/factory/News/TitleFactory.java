package by.onliner.news.factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.TitleViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class TitleFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new TitleViewObject(element.text());
    }
}
