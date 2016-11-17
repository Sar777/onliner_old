package by.onliner.news.Factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Common.Common;
import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;
import by.onliner.news.Structures.News.ViewsObjects.YoutubeViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class VideoFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        Element frameElement = element.getElementsByTag("iframe").first();
        if (frameElement == null)
            return null;

        final String youtubeVideoId = Common.getYoutubeVideoId(frameElement.attr("src"));
        if (youtubeVideoId.isEmpty())
            return null;

        return new YoutubeViewObject(youtubeVideoId);
    }
}
