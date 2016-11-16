package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Common.Common;
import by.onliner.news.Structures.News.ViewsObjects.SpanViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Добавление <p> </p>
 */
public class SpanBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        if (element.ownText().isEmpty()) {
            // Youtube
            if (element.getElementsByTag("iframe").size() > 0)
                return new VideoBuilder().build(element);
        }

        if (element.html().isEmpty())
            return null;

        if (!element.hasText())
            return null;

        return new SpanViewObject(Common.fromHtml(element.html()));
    }
}
