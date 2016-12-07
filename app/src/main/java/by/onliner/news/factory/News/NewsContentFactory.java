package by.onliner.news.factory.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Iterator;

import by.onliner.news.structures.news.News;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Фабрика компонентов окна чтения новости
 */
public class NewsContentFactory {
    public static ArrayList<ViewObject> create(News news) {
        Document document = Jsoup.parse(news.getContent());
        ArrayList<ViewObject> viewObjects = new ArrayList<>();

        // Блок заголовка новости
        viewObjects.add(new PreviewFactory(news).create(document.getElementsByClass("news-header__preview").first()));
        news.getAttributes().setId(Integer.parseInt(document.getElementsByClass("news_view_count").first().attr("news_id")));

        //doc.getElementById("comments").getElementsByClass("news-form__info-text").first().text()

        Element rootElement = document.getElementsByClass("news-text").first();
        if (rootElement == null)
            throw new IllegalArgumentException("News content factory not found news-text container from html");

        // Выбор фабрики
        for (Element element : rootElement.getAllElements()) {
            switch (element.tagName()) {
                case "p":
                    viewObjects.add(new SpanFactory().create(element));
                    break;
                case "hr":
                    viewObjects.add(new HRFactory().create(element));
                    break;
                case "h2":
                    viewObjects.add(new H2Factory().create(element));
                    break;
                case "ul":
                    viewObjects.add(new ULFactory().create(element));
                    break;
                case "blockquote":
                    viewObjects.add(new QuoteFactory().create(element));
                    break;
                case "div": {
                    // Изображения по одному и видео
                    if (element.className().contains("news-media_extended") || element.className().contains("news-media_condensed")) {
                        // Видео
                        if (element.getElementsByTag("iframe").size() > 0)
                            viewObjects.add(new YoutubeVideoFactory().create(element));
                        else
                            viewObjects.add(new ImageFactory().create(element));
                    }
                    // Заголовок
                    else if (element.className().contains("news-header__title"))
                        viewObjects.add(new TitleFactory().create(element));
                    //
                    else if (element.className().contains("news-entry__speech"))
                        viewObjects.add(new SpeechFactory().create(element));
                    // Голосование
                    else if (element.className().contains("news-vote") && !element.attr("data-post-id").isEmpty())
                        viewObjects.add(new VoteFactory().create(element));
                    // Слайдер изображений
                    else if (element.className().contains("news-media__gallery"))
                        viewObjects.add(new ImageSliderFactory().create(element));
                    break;
                }
                default:
                    break;
            }
        }

        return validateViewObjectFactory(viewObjects);
    }

    private static ArrayList<ViewObject> validateViewObjectFactory(ArrayList<ViewObject> objects) {
        Iterator<ViewObject> it = objects.iterator();
        while (it.hasNext()) {
            ViewObject object = it.next();
            if (object == null || !object.isValid())
                it.remove();
        }

        return objects;
    }
}
