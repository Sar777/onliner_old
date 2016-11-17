package by.onliner.news.Factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Common.Common;
import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.ViewsObjects.SpanViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Добавление <p> </p>
 */
public class SpanFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        if (element.ownText().isEmpty()) {
            // Youtube
            if (element.getElementsByTag("iframe").size() > 0)
                return new VideoFactory().create(element);
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
