package by.onliner.news.Parser.Parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import by.onliner.news.Common.Common;
import by.onliner.news.Parser.IContentParser;
import by.onliner.news.Structures.News.News;
import by.onliner.news.Structures.News.NewsHeader;

/**
 * Парсинг списка новостей
 */
public class NewsListParser implements IContentParser<String, ArrayList<News>> {
    private String mProject;

    public NewsListParser(String project) {
        this.mProject = project;
    }

    @Override
    public ArrayList<News> parse(String response) {
        ArrayList<News> list = new ArrayList<>();

        Document doc = Jsoup.parse(response);
        Elements elements = doc.getElementsByClass("news-tidings__item");
        for (Element element : elements) {
            NewsHeader header = new HeaderParser().parse(element);
            if (!header.isValid())
                continue;

            News news = new News(header);
            news.getAttributes().setUrl(Common.getUrlByProject(mProject) + news.getHeader().getUrl());
            news.getAttributes().setProject(mProject);
            list.add(news);
        }

        return list;
    }
}
