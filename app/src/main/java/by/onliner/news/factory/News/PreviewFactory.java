package by.onliner.news.factory.News;

import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.News;
import by.onliner.news.structures.news.viewsObjects.PreviewViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Формирование элемента с изображением
 */
public class PreviewFactory implements IFactoryViewObjects<Element, ViewObject> {
    private News mNews;

    public PreviewFactory(News news) {
        this.mNews = news;
    }

    @Override
    public ViewObject create(Element element) {
        for (Element child : element.getAllElements()) {
            if (child.className().isEmpty())
                continue;

            // Header
            if (child.className().contains("news-header__button_views"))
                mNews.getPreview().setView(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if (child.className().contains("news-header__button_comments"))
                mNews.getPreview().setComments(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if (child.className().contains("news-header__time"))
                mNews.getPreview().setPostDate(child.text());
            else if (child.className().contains("news-header__image")) {
                Matcher m = Pattern.compile("background-image: url\\((.*)\\);").matcher(child.attr("style"));
                if (m.matches())
                    mNews.getPreview().setImage(m.group(1).replace("'", ""));
            }
        }

        return new PreviewViewObject(mNews.getPreview());
    }
}
