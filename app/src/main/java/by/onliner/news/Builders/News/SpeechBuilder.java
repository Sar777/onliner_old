package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.SpeechViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class SpeechBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        return new SpeechViewObject(element.text());
    }
}
