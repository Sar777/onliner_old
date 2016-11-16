package by.onliner.news.Builders.News;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.NewsHeader;
import by.onliner.news.Structures.News.ViewsObjects.HeaderViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Формирование элемента с изображением
 */
public class HeaderBuilder implements IBuilder<NewsHeader, ViewObject> {
    @Override
    public ViewObject build(NewsHeader header) {
        return new HeaderViewObject(header);
    }
}
