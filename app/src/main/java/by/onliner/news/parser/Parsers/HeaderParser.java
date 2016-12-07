package by.onliner.news.parser.Parsers;

import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.onliner.news.parser.IContentParser;
import by.onliner.news.structures.news.NewsPreview;

/**
 * Парсинг заголовка новости
 */
public class HeaderParser implements IContentParser<Element, NewsPreview> {
    @Override
    public NewsPreview parse(Element element) {
        NewsPreview data = new NewsPreview();

        if (!element.attr("data-post-date").isEmpty())
            data.setPostDateUnix(Integer.parseInt(element.attr("data-post-date")));

        for (Element child : element.getAllElements()) {
            // Header basic
            if (child.className().contains("news-tidings__subtitle"))
                data.setTitle(child.children().first().child(0).text());
            else if (child.className().contains("news-tiles__subtitle"))
                data.setTitle(child.ownText());
            else if ((child.className().contains("news-tidings__comment") || child.className().contains("news-tiles__button_comments")) && !child.text().isEmpty())
                data.setComments(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if ((child.className().contains("news-tidings__button_views") || child.className().contains("news-tiles__button_views")) && !child.text().isEmpty())
                data.setView(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if ((child.className().contains("news-tidings__time") || child.className().contains("news-tiles__time")) && !child.text().isEmpty())
                data.setPostDate(child.text());
            else if (child.className().contains("news-tidings__stub") || child.className().contains("news-tiles__stub"))
                data.setUrl(child.attr("href"));
            else if (child.className().contains("news-tidings__image") || child.className().contains("news-tiles__image")) {
                Matcher m = Pattern.compile("background-image: url\\((.*)\\);").matcher(child.attr("style"));
                if (m.matches())
                    data.setImage(m.group(1));
            }

            // Attributes
            if (child.className().contains("button-style_excess button-style_small"))
                data.getAttributes().setUpd(true);
            else if ((child.className().contains("news-tidings__button_photo") || child.className().contains("news-tiles__button_photo")) && !child.text().isEmpty())
                data.getAttributes().setPhotos(Integer.parseInt(child.text().replaceAll(" ", "")));
            else if (child.className().contains("news-tidings__button_video") || child.className().contains("news-tiles__button_video"))
                data.getAttributes().setCamera(true);
        }

        return data;
    }
}
