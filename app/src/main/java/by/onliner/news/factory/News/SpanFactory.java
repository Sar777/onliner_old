package by.onliner.news.factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.common.Common;
import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.SpanViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Добавление <p> </p>
 */
public class SpanFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        if (element.ownText().isEmpty()) {
            // Youtube
            if (element.getElementsByTag("iframe").size() > 0)
                return new YoutubeVideoFactory().create(element);
        }

        if (element.html().isEmpty())
            return null;

        if (!element.hasText())
            return null;

        if (element.parent().className().equals("news-entry__speech"))
            return null;

        return new SpanViewObject(Common.fromHtml(element.html()));
    }
}
