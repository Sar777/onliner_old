package by.onliner.newsonlinerby.Parser.Parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.onliner.newsonlinerby.Parser.IContentParser;
import by.onliner.newsonlinerby.Structures.ContentNews;

/**
 * Created by Mi Air on 09.10.2016.
 */

public class FullNewsParser implements IContentParser<String, ContentNews> {
    @Override
    public ContentNews parse(String data) {
        Document doc = Jsoup.parse(data);
        Element wrapperElement = doc.getElementsByClass("news-container").first();
        if (wrapperElement == null)
            return null;

        ContentNews content = new ContentNews();

        for (Element element : wrapperElement.getAllElements()) {
            if (element.className().isEmpty())
                continue;

            if (element.className().indexOf("news-text") != -1) // Content
                content.setContent(element.html());
            // Header
            else if (element.className().indexOf("news-header__title") != -1) // Title
                content.getHeader().setTitle(element.text());
            else if (element.className().indexOf("news-header__button_views") != -1)
                 content.getHeader().setViews(Integer.parseInt(element.text().replaceAll(" ", "").trim()));
            else if (element.className().indexOf("news-header__button_comments") != -1)
                content.getHeader().setComments(Integer.parseInt(element.text().replaceAll(" ", "").trim()));
            else if (element.className().indexOf("news-header__time") != -1)
                content.getHeader().setPostDate(element.text());
            else if (element.className().indexOf("news-header__image") != -1) {
                Matcher m = Pattern.compile("background-image: url\\((.*)\\);").matcher(element.attr("style"));
                if (m.matches())
                    content.getHeader().setImage(m.group(1).replace("'", ""));
            }
        }

        return content;
    }
}
