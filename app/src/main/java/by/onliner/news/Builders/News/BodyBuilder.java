package by.onliner.news.Builders.News;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import by.onliner.news.App;
import by.onliner.news.Builders.IBuilder;
import by.onliner.news.Common.Common;
import by.onliner.news.R;
import by.onliner.news.Structures.News.News;

/**
 * Формирование блока новости
 */
public class BodyBuilder implements IBuilder<View, View> {
    private News mContent;
    private Activity mActivity;

    public BodyBuilder(Activity activity, News content) {
        this.mActivity = activity;
        this.mContent = content;
    }

    @Override
    public View build(View layout2) {
        Document doc = Jsoup.parse(mContent.getContent());

        LinearLayout layout = new LinearLayout(App.getContext());

        for (Element element : doc.getAllElements()) {
            switch (element.tagName()) {
                case "p": {
                    /*if (element.ownText().isEmpty()) {
                        // Youtube
                        if (element.getElementsByTag("iframe").size() > 0)
                            new VideoBuilder(element, mActivity).build(layout);
                        continue;
                    }*/

                    break;
                }
                case "ul": {
                    // Список
                    Spanned result = Common.fromHtml(element.html());

                    TextView textView = new TextView(App.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(15, 0, 0, 25);
                    textView.setLayoutParams(layoutParams);

                    textView.setText(result);
                    textView.setTextColor(Color.BLACK);
                    textView.setLinkTextColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerNewsLinkText, null));
                    textView.setTextSize(15);

                    layout.addView(textView);
                    break;
                }
                case "h2": {
                    TextView textView = new TextView(App.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 10, 0, 15);
                    textView.setLayoutParams(layoutParams);
                    textView.setText(element.text());
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(18);
                    textView.setTypeface(null, Typeface.BOLD);
                    layout.addView(textView);
                    break;
                }
                case "div": {
                    // Изображения по одному и видео
                    if (element.className().indexOf("news-media_extended") != -1 || element.className().indexOf("news-media_condensed") != -1) {
                    }
                    // Слайдер изображений
                    else if (element.className().indexOf("news-media__gallery") != -1)
                        new ImageSliderBuilder(element, mActivity).build(layout);
                    // Голосование
                    else if (element.className().indexOf("news-vote") != -1 && element.attr("data-post-id") != "")
                        layout.addView(new VoteBuilder().build(element));
                    else if (element.className().indexOf("news-header__title") != -1) {
                        TextView textView = new TextView(App.getContext());

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, 30);
                        textView.setLayoutParams(layoutParams);

                        textView.setTextColor(Color.BLACK);
                        textView.setText(element.text());
                        textView.setTextSize(18);
                        textView.setTypeface(null, Typeface.BOLD);
                        layout.addView(textView);
                    }
                    break;
                }
                default:
                    continue;
            }
        }

        return layout;
    }
}
