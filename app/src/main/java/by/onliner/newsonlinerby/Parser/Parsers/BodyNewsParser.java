package by.onliner.newsonlinerby.Parser.Parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.onliner.newsonlinerby.Parser.IContentParser;
import by.onliner.newsonlinerby.Structures.News.News;

/**
 * Парсинг содержимого новости и атрибутов
 */
public class BodyNewsParser implements IContentParser<String, News> {
    @Override
    public News parse(String data) {
        Document doc = Jsoup.parse(data);

        News news = new News();
        news.getAttributes().setId(Integer.parseInt(doc.getElementsByClass("news_view_count").first().attr("news_id")));
        news.getAttributes().setProject(doc.getElementById("commentsList").attr("data-project"));

        Element wrapperElement = doc.getElementsByClass("news-container").first();
        if (wrapperElement == null)
            return news;

        for (Element element : wrapperElement.getAllElements()) {
            if (element.className().isEmpty())
                continue;

            if (element.className().indexOf("news-text") != -1) // Content
                news.setContent(element.html());
            // Header
            else if (element.className().indexOf("news-header__title") != -1) // Title
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
