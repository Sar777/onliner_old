package by.onliner.newsonlinerby.Parser.Parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Parser.IContentParser;
import by.onliner.newsonlinerby.Structures.HeaderNews;

/**
 * Created by orion on 23.10.16.
 */

public class NewsListParser implements IContentParser<String, ArrayList<HeaderNews>> {
    @Override
    public ArrayList<HeaderNews> parse(String response) {
        ArrayList<HeaderNews> list = new ArrayList<>();

        Document doc = Jsoup.parse(response);
        Elements elements = doc.getElementsByClass("news-tidings__item");
        for (Element element : elements) {
            HeaderNews header = new HeaderParser().parse(element);
            if (!header.isValid())
                continue;

            list.add(header);
        }

        return list;
    }
}
