package by.onliner.newsonlinerby.Builder.News;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builder.IBuilder;
import by.onliner.newsonlinerby.Listeners.FullScreenImageListener;
import by.onliner.newsonlinerby.R;
import eu.fiskur.simpleviewpager.ImageURLLoader;
import eu.fiskur.simpleviewpager.SimpleViewPager;

/**
 * Created by Mi Air on 18.10.2016.
 */

public class ImageSliderBuilder implements IBuilder<LinearLayout, LinearLayout> {
    private Element element;
    private Activity mActivity;

    public ImageSliderBuilder(Element element, Activity activity) {
        this.element = element;
        this.mActivity = activity;
    }

    @Override
    public LinearLayout build(LinearLayout layout) {
        final ArrayList<String> imagesUrl = new ArrayList<>();
        for (Element imageElement : element.getElementsByTag("img")) {
            if (imageElement.attr("data-src").isEmpty())
                continue;

            imagesUrl.add(imageElement.attr("data-src"));
        }

        if (imagesUrl.isEmpty())
            throw new ExceptionInInitializerError("ImageSliderBuilder empty images url container");

        SimpleViewPager viewPager = new SimpleViewPager(App.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700);
        viewPager.setLayoutParams(layoutParams);

        viewPager.setImageUrls(imagesUrl.toArray(new String[imagesUrl.size()]), new ImageURLLoader() {
            @Override
            public void loadImage(ImageView view, String url) {
                view.setScaleType(ImageView.ScaleType.FIT_START);
                view.setAdjustViewBounds(true);
                view.setOnClickListener(new FullScreenImageListener(mActivity, imagesUrl));

                Picasso.with(App.getContext()).
                        load(url).
                        error(R.drawable.ic_broken_image).
                        memoryPolicy(MemoryPolicy.NO_CACHE).
                        into(view);
            }
        });

        viewPager.showIndicator(Color.WHITE, Color.BLACK);

        layout.addView(viewPager);
        return layout;
    }
}
