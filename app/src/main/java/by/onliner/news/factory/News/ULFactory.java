package by.onliner.news.factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.common.Common;
import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.ULViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Тег <hr> </hr>
 */
public class ULFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        return new ULViewObject(Common.fromHtml(element.html()));
    }
}
