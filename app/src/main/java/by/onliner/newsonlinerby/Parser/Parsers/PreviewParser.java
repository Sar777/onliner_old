package by.onliner.newsonlinerby.Parser.Parsers;

import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.onliner.newsonlinerby.Parser.IContentParser;
import by.onliner.newsonlinerby.Structures.Preview.PreviewData;

/**
 * Created by Mi Air on 30.09.2016.
 */

public class PreviewParser implements IContentParser<Element, PreviewData> {
    @Override
    public PreviewData parse(Element element) {
        PreviewData data = new PreviewData();

        data.setDateUnix(Integer.parseInt(element.attr("data-post-date")));
        for (Element child : element.getAllElements()) {
            if (child.className().indexOf("news-tidings__subtitle") != -1)
                data.setTitle(child.text());
            else if (child.className().indexOf("news-tidings__speech") != -1)
                data.setText(child.text());
            else if (child.className().indexOf("news-tidings__comment") != -1)
                data.setComments(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if (child.className().indexOf("news-tidings__button_views") != -1)
                data.setViews(Integer.parseInt(child.text().replaceAll(" ", "").trim()));
            else if (child.className().indexOf("news-tidings__time") != -1)
                data.setDate(child.text());
            else if (child.className().indexOf("button-style_excess button-style_small") != -1)
                data.setUpd(true);
            else if (child.className().indexOf("news-tidings__image") != -1) {
                Matcher m = Pattern.compile("background-image: url\\((.*)\\);").matcher(child.attr("style"));
                if (m.matches())
                    data.setImageUrl(m.group(1));
            }
        }

        return data;
    }
}
