package by.onliner.newsonlinerby.Parser.Parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Parser.IContentParser;
import by.onliner.newsonlinerby.Structures.News.HeaderNews;
import by.onliner.newsonlinerby.Structures.News.News;

/**
 * Парсинг списка новостей
 */
public class NewsListParser implements IContentParser<String, ArrayList<News>> {
    private String mUrl;

    public NewsListParser(String url) {
        this.mUrl = url;
    }

    @Override
    public ArrayList<News> parse(String response) {
        ArrayList<News> list = new ArrayList<>();

        Document doc = Jsoup.parse(response);
        Elements elements = doc.getElementsByClass("news-tidings__item");
        for (Element element : elements) {
            HeaderNews header = new HeaderParser().parse(element);
            News news = new News(header);
            news.getAttributes().setUrl(mUrl + news.getHeader().getUrl());

            if (!news.getHeader().isValid())
                continue;

            list.add(news);
        }

        return list;
    }
}
