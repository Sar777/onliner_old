package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.H2ViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Тег <h2> </h2>
 */
public class H2Builder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        return new H2ViewObject(element.text());
    }
}
