package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.ImageViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Формирование элемента с изображением
 */
public class ImageBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {

        String urlImage = "", imageDescription = "";
        for (final Element child : element.getAllElements()) {
            if (child.className().contains("news-media__image")) {
                if (child.attr("src").isEmpty() || !urlImage.isEmpty())
                    continue;

                urlImage = child.attr("src");
            }
            else if (child.className().contains("news-media__subtitle")) {
                imageDescription = child.text();
            }
        }

        if (urlImage.isEmpty())
            return null;

        return new ImageViewObject(urlImage, imageDescription);
    }
}
