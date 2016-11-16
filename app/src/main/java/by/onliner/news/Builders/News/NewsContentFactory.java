package by.onliner.news.Builders.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Фабрика компонентов окна чтения новости
 */
public class NewsContentFactory {
    public static ArrayList<ViewObject> build(String html) {
        Document document = Jsoup.parse(html);

        ArrayList<ViewObject> viewObjects = new ArrayList<>(document.getAllElements().size());

        // Выбор билдера для обработки элемента
        for (Element element : document.getAllElements()) {
            switch (element.tagName()) {
                case "p": {
                    ViewObject object = new SpanBuilder().build(element);
                    if (object != null)
                        viewObjects.add(object);
                    break;
                }
                case "hr":
                    viewObjects.add(new HRBuilder().build(element));
                    break;
                case "h2":
                    viewObjects.add(new H2Builder().build(element));
                    break;
                case "ul":
                    viewObjects.add(new ULBuilder().build(element));
                    break;
                case "blockquote": {
                    ViewObject object = new QuoteBuilder().build(element);
                    if (object != null)
                        viewObjects.add(object);
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
                    // Заголовок
                    else if (element.className().indexOf("news-header__title") != -1)
                        viewObjects.add(new ImageBuilder().build(element));
                    // Голосование
                    else if (element.className().indexOf("news-vote") != -1 && !element.attr("data-post-id").isEmpty())
                        viewObjects.add(new VoteBuilder().build(element));
                    break;
                }
                default:
                    break;
            }
        }

        return viewObjects;
    }
}
