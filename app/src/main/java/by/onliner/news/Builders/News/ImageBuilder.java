package by.onliner.news.Builders.News;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;

import by.onliner.news.App;
import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Listeners.FullScreenImageListener;
import by.onliner.news.R;

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
            if (child.className().contains("news-media__image")) {
                if (child.attr("src").isEmpty())
                    continue;

                LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.layout_image_news, null);

                final ImageView imageView = (ImageView)view.findViewById(R.id.img_view_news);
                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_loadin_view_news);
                final Button buttonLoadingImg = (Button) view.findViewById(R.id.bt_repeat_load_image);
                final LinearLayout linearLayoutRepeat = (LinearLayout) view.findViewById(R.id.l_group_repeat_loading_img);
                final String imageURL = child.attr("src");

                buttonLoadingImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        asyncLoadingImage(imageURL, progressBar, imageView, linearLayoutRepeat);
                    }
                });

                asyncLoadingImage(imageURL, progressBar, imageView, linearLayoutRepeat);

                // Открытие полного режима просмотра
                imageView.setOnClickListener(new FullScreenImageListener(mActivity, new ArrayList<>(Arrays.asList(child.attr("src")))));

                ((LinearLayout)layout).addView(view);
            }
            else if (child.className().contains("news-media__subtitle")) {
                View view = new TextView(App.getContext());

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 25);
                layoutParams.gravity = Gravity.CENTER;
                view.setLayoutParams(layoutParams);

                ((TextView)view).setGravity(Gravity.CENTER_HORIZONTAL);
                ((TextView)view).setTextColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerBlockquoteText, null));
                ((TextView)view).setTextSize(12);
                ((TextView)view).setText(child.text());

                ((LinearLayout)layout).addView(view);
            }
        }
        return layout;
    }

    private void asyncLoadingImage(final String url, final ProgressBar progressBar, final ImageView imageView, final LinearLayout linearLayoutRepeat) {
        linearLayoutRepeat.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Picasso.with(App.getContext()).
                        load(url).
                        resize(0, 300).
                        into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                progressBar.setVisibility(View.GONE);
                                linearLayoutRepeat.setVisibility(View.VISIBLE);
                            }
                        });
            }
        });
    }
}
