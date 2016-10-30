package by.onliner.newsonlinerby.Common;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by orion on 30.10.16.
 */
public class Common {

    /**
     * Spanned из html текста
     *
     * @param html Html текст
     * @return the Spanned для TextView
     */
    public static Spanned fromHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        else
            return Html.fromHtml(html);
    }
}