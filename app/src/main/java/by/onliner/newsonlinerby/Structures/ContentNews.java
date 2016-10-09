package by.onliner.newsonlinerby.Structures;

/**
 * Created by Mi Air on 09.10.2016.
 */

public class ContentNews {
    private HeaderNews header;
    private String content;

    public ContentNews() {
        header = new HeaderNews();
        content = "";
    }

    public ContentNews(HeaderNews header, String title, String content) {
        this.header = header;
        this.content = content;
    }

    public ContentNews(HeaderNews header) {
        this.header = header;
    }

    public HeaderNews getHeader() {
        return header;
    }

    public void setHeader(HeaderNews header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
