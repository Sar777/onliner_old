package by.onliner.news.Factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Common.Common;
import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.ViewsObjects.ULViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Тег <hr> </hr>
 */
public class ULFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new ULViewObject(Common.fromHtml(element.html()));
    }
}
