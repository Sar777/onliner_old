package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Common.Common;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;
import by.onliner.news.Structures.News.ViewsObjects.YoutubeViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class VideoBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        Element frameElement = element.getElementsByTag("iframe").first();
        if (frameElement == null)
            return null;

        final String youtubeVideoId = Common.getYoutubeVideoId(frameElement.attr("src"));
        if (youtubeVideoId.isEmpty())
            return null;

        return new YoutubeViewObject(youtubeVideoId);
    }
}
