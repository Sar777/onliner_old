package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.TitleViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class TitleBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        return new TitleViewObject(element.text());
    }
}
