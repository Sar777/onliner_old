package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.HRViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Тег <hr> </hr>
 */
public class HRBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        return new HRViewObject();
    }
}
