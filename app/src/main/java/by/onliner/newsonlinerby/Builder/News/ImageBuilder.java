package by.onliner.newsonlinerby.Builder.News;

import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builder.IBuilder;
import by.onliner.newsonlinerby.R;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class ImageBuilder implements IBuilder<View, View> {
    private Element element;

    public ImageBuilder(Element element) {
        this.element = element;
    }

    @Override
    public View build(View layout) {
        for (Element child : element.getAllElements()) {
            View view = null;
            if (child.className().indexOf("news-media__image") != -1) {
                view = new ImageView(App.getContext());
                ((ImageView)view).setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                // Sub title img
                if (element.getElementsByClass("news-media__subtitle").isEmpty())
                    layoutParams.setMargins(0, 5, 0, 30);
                else
                    layoutParams.setMargins(0, 5, 0, 10);

                view.setLayoutParams(layoutParams);
                Picasso.with(App.getContext()).load(child.attr("src")).into((ImageView)view);
            }
            else if (child.className().indexOf("news-media__subtitle") != -1) {
                view = new TextView(App.getContext());
                ((TextView)view).setGravity(Gravity.CENTER_HORIZONTAL);
                ((TextView)view).setTextColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerBlockquoteText, null));
                ((TextView)view).setTextSize(12);
                ((TextView)view).setText(child.text());
                ((TextView)view).append("\n");
            }

            if (view != null)
                ((LinearLayout)layout).addView(view);
        }
        return layout;
    }
}
