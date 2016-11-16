package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;
import by.onliner.news.Structures.News.ViewsObjects.VoteViewObject;

/**
 * Формирование опроса
 */
public class VoteBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        ArrayList<String> options = new ArrayList<>();

        for (Element checkboxElement : element.getElementsByClass("news-form__checkbox-item"))
            options.add(checkboxElement.getElementsByClass("news-form__checkbox-sign").first().text());

        return new VoteViewObject(element.getElementsByClass("news-vote__title").first().text(), element.getElementsByClass("news-vote__speech").first().text(), options);
    }
}
