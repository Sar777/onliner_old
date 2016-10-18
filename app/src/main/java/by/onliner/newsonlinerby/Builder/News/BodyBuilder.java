package by.onliner.newsonlinerby.Builder.News;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.Builder.IBuilder;
import by.onliner.newsonlinerby.CustomViews.QuoteTextView;
import by.onliner.newsonlinerby.R;

/**
 * Created by Mi Air on 13.10.2016.
 */

public class BodyBuilder implements IBuilder<View, String> {
    private String[] tagNames;
    private String data;

    public BodyBuilder(String data, String... tags) {
        this.data = data;
    }

    @Override
    public String build(View layout) {
        Document doc = Jsoup.parse(data);

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
                    textView.setText(result);
                    textView.append("\n");
                    textView.setTextColor(Color.BLACK);
                    textView.setLinkTextColor(ResourcesCompat.getColor(App.getContext().getResources(), R.color.colorOnlinerNewsLinkText, null));
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
                    else if (element.className().indexOf("news-header__title") != -1) {
                        TextView textView = new TextView(App.getContext());
                        textView.setTextColor(Color.BLACK);
                        textView.setText(new Spanny().append(element.text(), new RelativeSizeSpan(1.2f), new StyleSpan(Typeface.BOLD)));
                        textView.append("\n");
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

        return "";
    }
}
