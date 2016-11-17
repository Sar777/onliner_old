package by.onliner.news.Factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.ViewsObjects.TitleViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class TitleFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new TitleViewObject(element.text());
    }
}
