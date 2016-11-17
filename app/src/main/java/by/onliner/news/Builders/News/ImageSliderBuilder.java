package by.onliner.news.Builders.News;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Structures.News.ViewsObjects.ImageSliderViewObject;
import by.onliner.news.Structures.News.ViewsObjects.ViewObject;

/**
 * Created by Mi Air on 18.10.2016.
 */

public class ImageSliderBuilder implements IBuilder<Element, ViewObject> {
    @Override
    public ViewObject build(Element element) {
        final ArrayList<String> imagesUrls = new ArrayList<>();
        for (Element imageElement : element.getElementsByTag("img")) {
            if (imageElement.attr("data-src").isEmpty())
                continue;

            imagesUrls.add(imageElement.attr("data-src"));
        }

        if (imagesUrls.isEmpty())
            throw new ExceptionInInitializerError("ImageSliderBuilder empty images url container");

        String imageDescription = "";
        Element elementDesc = element.getElementsByClass("news-media__subtitle").first();
        if (elementDesc != null)
            imageDescription = elementDesc.text();

        return new ImageSliderViewObject(imagesUrls, imageDescription);
    }
}
