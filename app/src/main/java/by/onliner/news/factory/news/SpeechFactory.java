package by.onliner.news.factory.news;

import org.jsoup.nodes.Element;

import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.SpeechViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class SpeechFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new SpeechViewObject(element.text());
    }
}
