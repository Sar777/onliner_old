package by.onliner.newsonlinerby.Builder.News;

import android.text.Layout;
import android.text.style.AlignmentSpan;
import android.text.style.SubscriptSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builder.IBuilder;

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
                layoutParams.setMargins(0, 5, 0, 30);
                view.setLayoutParams(layoutParams);
                Picasso.with(App.getContext())
                        .load(child.attr("src"))
                        //.placeholder(R.drawable.ic_onliner_logo)
                        //.error(R.drawable.user_placeholder_error)
                        .into((ImageView)view);
            }
            else if (child.className().indexOf("news-media__subtitle") != -1) {
                view = new TextView(App.getContext());
                ((TextView)view).setText(new Spanny().append(child.text(), new SubscriptSpan(), new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)));
            }

            if (view != null)
                ((LinearLayout)layout).addView(view);
        }
        return layout;
    }
}
