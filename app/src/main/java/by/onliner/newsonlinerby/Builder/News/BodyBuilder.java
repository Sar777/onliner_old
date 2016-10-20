package by.onliner.newsonlinerby.Builder.News;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
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
import by.onliner.newsonlinerby.Builder.IBuilder;
import by.onliner.newsonlinerby.CustomViews.QuoteTextView;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.ContentNews;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class BodyBuilder implements IBuilder<View, View> {
    private String[] tagNames;
    private ContentNews content;

    public BodyBuilder(ContentNews content, String... tags) {
        this.content = content;
    }

    @Override
    public View build(View layout2) {
        Document doc = Jsoup.parse(content.getContent());

        LinearLayout layout = new LinearLayout(App.getContext());

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.setMargins(0, 10, 0, 0);
        layout.setLayoutParams(relativeParams);

        layout.setPadding(10, 15, 10, 5);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        for (Element element : doc.getAllElements()) {
            switch (element.tagName()) {
                case "p": {
                    if (element.text().isEmpty())
                        continue;

                    Spanned result;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        result = Html.fromHtml(element.html(), Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        result = Html.fromHtml(element.html());
                    }

                    TextView textView = new TextView(App.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 25);
                    textView.setLayoutParams(layoutParams);

                    textView.setText(result);
                    textView.setTextColor(Color.BLACK);
                    textView.setLinkTextColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerNewsLinkText, null));
                    textView.setTextSize(15);

                    ((LinearLayout)layout).addView(textView);
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
                    ((LinearLayout)layout).addView(imageView);
                    break;
                }
                case "div": {
                    // Images & Media
                    if (element.className().indexOf("news-media_extended") != -1 || element.className().indexOf("news-media_condensed") != -1)
                        new ImageBuilder(element).build(layout);
                    else if (element.className().indexOf("news-media__gallery") != -1)
                        new ImageSliderBuilder(element).build((LinearLayout) layout);
                    else if (element.className().indexOf("news-header__title") != -1) {
                        TextView textView = new TextView(App.getContext());

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, 30);
                        textView.setLayoutParams(layoutParams);

                        textView.setTextColor(Color.BLACK);
                        textView.setText(element.text());
                        textView.setTextSize(18);
                        textView.setTypeface(null, Typeface.BOLD);
                        ((LinearLayout)layout).addView(textView);
                    }
                    break;
                }
                case "blockquote": {
                    QuoteTextView textView = new QuoteTextView(App.getContext());
                    textView.setText(element.text());
                    ((LinearLayout)layout).addView(textView);
                    break;
                }
                default:
                    continue;
            }
        }

        return layout;
    }
}
