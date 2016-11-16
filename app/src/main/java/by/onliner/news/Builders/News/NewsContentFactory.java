package by.onliner.news.Builders.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

import by.onliner.news.Common.Common;
import by.onliner.news.Structures.News.ViewsObjects.HRViewObject;
import by.onliner.news.Structures.News.ViewsObjects.QuoteViewObject;
import by.onliner.news.Structures.News.ViewsObjects.SpanViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Created by orion on 15.11.16.
 */

public class NewsContentFactory {
    public static ArrayList<ViewObject> build(String html) {
        Document document = Jsoup.parse(html);

        ArrayList<ViewObject> viewObjects = new ArrayList<>(document.getAllElements().size());

        // Выбор билдера для обработки элемента
        for (Element element : document.getAllElements()) {
            switch (element.tagName()) {
                case "p": {
                    if (element.ownText().isEmpty()) {
                        // Youtube
                        if (element.getElementsByTag("iframe").size() > 0) {
                            ViewObject object = new VideoBuilder().build(element);
                            if (object != null)
                                viewObjects.add(object);
                        }
                    }

                    viewObjects.add(new SpanViewObject(Common.fromHtml(element.html())));
                    break;
                }
                case "hr": {
                    viewObjects.add(new HRViewObject());
                    break;
                }
                case "div": {
                    // Изображения по одному и видео
                    if (element.className().indexOf("news-media_extended") != -1 || element.className().indexOf("news-media_condensed") != -1) {
                        // Видео
                        if (element.getElementsByTag("iframe").size() > 0) {
                            ViewObject object = new VideoBuilder().build(element);
                            if (object != null)
                                viewObjects.add(object);
                        }
                        else {
                            ViewObject object = new ImageBuilder().build(element);
                            if (object != null)
                                viewObjects.add(object);
                        }
                    }
                    break;
                }
                case "blockquote": {
                    // Ignore instagram
                    if (element.className().indexOf("instagram-media") != -1)
                        continue;

                    viewObjects.add(new QuoteViewObject(element.text()));
                    break;
                }
                default:
                    break;
            }
        }

        return viewObjects;
    }
}
