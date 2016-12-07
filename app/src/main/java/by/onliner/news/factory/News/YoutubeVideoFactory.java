package by.onliner.news.factory.News;

import org.jsoup.nodes.Element;

import by.onliner.news.common.Common;
import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.ViewObject;
import by.onliner.news.structures.news.viewsObjects.YoutubeViewObject;

/**
 * Добавление плеера Youtube на экран
 */
public class YoutubeVideoFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        Element frameElement = element.getElementsByTag("iframe").first();
        if (frameElement == null)
            return null;

        if (!frameElement.attr("strc").contains("youtube"))
            return null;

        final String youtubeVideoId = Common.getYoutubeVideoId(frameElement.attr("src"));
        if (youtubeVideoId.isEmpty())
            return null;

        return new YoutubeViewObject(youtubeVideoId);
    }
}
