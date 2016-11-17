package by.onliner.news.Factory.News;

import by.onliner.news.Factory.IFactoryViewObjects;
import by.onliner.news.Structures.News.NewsHeader;
import by.onliner.news.Structures.News.ViewsObjects.HeaderViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Формирование элемента с изображением
 */
public class HeaderFactory implements IFactoryViewObjects<NewsHeader, ViewObject> {
    @Override
    public ViewObject create(NewsHeader header) {
        return new HeaderViewObject(header);
    }
}
