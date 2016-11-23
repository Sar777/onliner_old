package by.onliner.news.Common;

import android.text.Html;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String getYoutubeVideoId(String url) {
        Pattern pattern = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches())
            return matcher.group(1);

        return "";
    }

    public static String getUrlByProject(String project) {
        return "https://" + project + ".onliner.by";
    }

    public static long getUnixTimeNow() {
        return System.currentTimeMillis() / 1000L;
    }
}