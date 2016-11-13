package by.onliner.newsonlinerby.Builders.News;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builders.IBuilder;
import by.onliner.newsonlinerby.Common.Common;
import by.onliner.newsonlinerby.CustomViews.QuoteTextView;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.News.News;

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

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.setMargins(0, 10, 0, 0);
        layout.setLayoutParams(relativeParams);

        layout.setPadding(23, 15, 23, 5);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        for (Element element : doc.getAllElements()) {
            switch (element.tagName()) {
                case "p": {

                    if (element.ownText().isEmpty()) {
                        continue;
                    }

                    Spanned result = Common.fromHtml(element.html());

                    TextView textView = new TextView(App.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 25);
                    textView.setLayoutParams(layoutParams);

                    textView.setText(result);
                    textView.setTextColor(Color.BLACK);
                    textView.setLinkTextColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerNewsLinkText, null));
                    textView.setTextSize(15);

                    layout.addView(textView);
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
                case "hr": {
                    ImageView imageView = new ImageView(App.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER;
                    layoutParams.bottomMargin = 15;
                    layoutParams.topMargin = 15;
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageResource(R.drawable.ic_hr_tag_onliner);
                    layout.addView(imageView);
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
                        // Видео
                        if (element.getElementsByTag("iframe").size() > 0)
                            new VideoBuilder(element, mActivity).build(layout);
                        else
                            new ImageBuilder(element, mActivity).build(layout);
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
                case "blockquote": {
                    // Ignore instagram
                    if (element.className().indexOf("instagram-media") != -1)
                        continue;

                    QuoteTextView textView = new QuoteTextView(App.getContext());
                    textView.setText(element.text());
                    layout.addView(textView);
                    break;
                }
                default:
                    continue;
            }
        }

        return layout;
    }
}
