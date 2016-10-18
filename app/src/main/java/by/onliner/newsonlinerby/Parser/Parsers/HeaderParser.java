package by.onliner.newsonlinerby.Parser.Parsers;

import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.onliner.newsonlinerby.Parser.IContentParser;
import by.onliner.newsonlinerby.Structures.HeaderNews;

/**
 * Created by Mi Air on 30.09.2016.
 */

public class HeaderParser implements IContentParser<Element, HeaderNews> {
    @Override
    public HeaderNews parse(Element element) {
        HeaderNews data = new HeaderNews();

        if (!element.attr("data-post-date").isEmpty())
            data.setPostDateUnix(Integer.parseInt(element.attr("data-post-date")));

        for (Element child : element.getAllElements()) {
            // Header basic
            if (child.className().indexOf("news-tidings__subtitle") != -1)
                data.setTitle(child.children().first().child(0).text());
            else if (child.className().indexOf("news-tiles__subtitle") != -1)
                data.setTitle(child.ownText());
            else if ((child.className().indexOf("news-tidings__comment") != -1 || child.className().indexOf("news-tiles__button_comments") != -1) && !child.text().isEmpty())
                data.setComments(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if ((child.className().indexOf("news-tidings__button_views") != -1 || child.className().indexOf("news-tiles__button_views") != -1) && !child.text().isEmpty())
                data.setViews(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if ((child.className().indexOf("news-tidings__time") != -1 || child.className().indexOf("news-tiles__time") != -1) && !child.text().isEmpty())
                data.setPostDate(child.text());
            else if (child.className().indexOf("news-tidings__stub") != -1 || child.className().indexOf("news-tiles__stub") != -1)
                data.setUrl(child.attr("href"));
            else if (child.className().indexOf("news-tidings__image") != -1 || child.className().indexOf("news-tiles__image") != -1) {
                Matcher m = Pattern.compile("background-image: url\\((.*)\\);").matcher(child.attr("style"));
                if (m.matches())
                    data.setImage(m.group(1));
            }

            // Attributes
            if (child.className().indexOf("button-style_excess button-style_small") != -1)
                data.getAttributes().setUpd(true);
            else if ((child.className().indexOf("news-tidings__button_photo") != -1 || child.className().indexOf("news-tiles__button_photo") != -1) && !child.text().isEmpty())
                data.getAttributes().setPhotos(Integer.parseInt(child.text().replaceAll(" ", "")));
            else if (child.className().indexOf("news-tidings__button_video") != -1 || child.className().indexOf("news-tiles__button_video") != -1)
                data.getAttributes().setCamera(true);
        }

        return data;
    }
}
