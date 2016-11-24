package by.onliner.news.Parser.Parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.onliner.news.Common.Common;
import by.onliner.news.Parser.IContentParser;
import by.onliner.news.Structures.News.News;

/**
 * Парсинг содержимого новости и атрибутов
 */
public class BodyNewsParser implements IContentParser<Document, News> {
    @Override
    public News parse(Document doc) {
        Element wrapperElement = doc.getElementsByClass("news-container").first();
        if (wrapperElement == null)
            throw new IllegalArgumentException("Not found base news container in HTML");

        News news = new News(doc);
        news.getAttributes().setId(Integer.parseInt(doc.getElementsByClass("news_view_count").first().attr("news_id")));
        news.getAttributes().setProject(Common.getProjectByUrl(doc.baseUri()));
        news.getAttributes().setUrl(doc.baseUri());

        for (Element element : wrapperElement.getAllElements()) {
            if (element.className().isEmpty())
                continue;

            // Header
            if (element.className().indexOf("news-header__title") != -1) // Title
                news.getHeader().setTitle(element.text());
            else if (element.className().indexOf("news-header__button_views") != -1)
                 news.getHeader().setViews(Integer.parseInt(element.text().replaceAll(" ", "").trim()));
            else if (element.className().indexOf("news-header__button_comments") != -1)
                news.getHeader().setComments(Integer.parseInt(element.text().replaceAll(" ", "").trim()));
            else if (element.className().indexOf("news-header__time") != -1)
                news.getHeader().setPostDate(element.text());
            else if (element.className().indexOf("news-header__image") != -1) {
                Matcher m = Pattern.compile("background-image: url\\((.*)\\);").matcher(element.attr("style"));
                if (m.matches())
                    news.getHeader().setImage(m.group(1).replace("'", ""));
            }
        }

        return news;
    }
}
