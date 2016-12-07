package by.onliner.news.factory.News;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

import by.onliner.news.factory.IFactoryViewObjects;
import by.onliner.news.structures.news.viewsObjects.ImageSliderViewObject;
import by.onliner.news.structures.news.viewsObjects.ViewObject;

/**
 * Created by Mi Air on 18.10.2016.
 */

public class ImageSliderFactory implements IFactoryViewObjects<Element, ViewObject> {
    @Override
    public ViewObject create(Element element) {
        final ArrayList<String> imagesUrls = new ArrayList<>();
        for (Element imageElement : element.getElementsByTag("img")) {
            if (imageElement.attr("data-src").isEmpty())
                continue;

            imagesUrls.add(imageElement.attr("data-src"));
        }

        if (imagesUrls.isEmpty())
            throw new ExceptionInInitializerError("ImageSliderFactory empty images url container");

        String imageDescription = "";
        Element elementDesc = element.getElementsByClass("news-media__subtitle").first();
        if (elementDesc != null)
            imageDescription = elementDesc.text();

        return new ImageSliderViewObject(imagesUrls, imageDescription);
    }
}
