package by.onliner.newsonlinerby.Builders.News;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builders.IBuilder;
import by.onliner.newsonlinerby.Listeners.FullScreenImageListener;
import by.onliner.newsonlinerby.R;

/**
 * Формирование элемента с изображением
 */
public class ImageBuilder implements IBuilder<View, View> {
    private Element mElement;
    private Activity mActivity;

    public ImageBuilder(Element element, Activity activity) {
        this.mElement = element;
        this.mActivity = activity;
    }

    @Override
    public View build(View layout) {
        for (final Element child : mElement.getAllElements()) {
            View view = null;
            if (child.className().contains("news-media__image")) {

                if (child.attr("src").isEmpty())
                    continue;

                view = new ImageView(App.getContext());
                ((ImageView)view).setScaleType(ImageView.ScaleType.FIT_START);
                ((ImageView)view).setAdjustViewBounds(true);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.gravity = Gravity.CENTER;

                // Sub title img
                if (mElement.getElementsByClass("news-media__subtitle").isEmpty())
                    layoutParams.setMargins(0, 5, 0, 30);
                else
                    layoutParams.setMargins(0, 5, 0, 10);

                view.setLayoutParams(layoutParams);

                // Открытие полного режима просмотра
                view.setOnClickListener(new FullScreenImageListener(mActivity, new ArrayList<>(Arrays.asList(child.attr("src")))));

                {
                    final View finalView = view;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            Picasso.with(App.getContext()).
                                    load(child.attr("src")).
                                    resize(0, 300).
                                    error(R.drawable.ic_broken_image).
                                    into((ImageView) finalView);
                        }
                    });
                }
            }
            else if (child.className().contains("news-media__subtitle")) {
                view = new TextView(App.getContext());

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 25);
                view.setLayoutParams(layoutParams);

                ((TextView)view).setGravity(Gravity.CENTER_HORIZONTAL);
                ((TextView)view).setTextColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerBlockquoteText, null));
                ((TextView)view).setTextSize(12);
                ((TextView)view).setText(child.text());
            }

            if (view != null)
                ((LinearLayout)layout).addView(view);
        }
        return layout;
    }
}
