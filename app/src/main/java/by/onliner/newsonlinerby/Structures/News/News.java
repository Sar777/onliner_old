package by.onliner.newsonlinerby.Structures.News;

import by.onliner.newsonlinerby.Structures.HeaderNews;

/**
 * Created by Mi Air on 09.10.2016.
 */

public class News {
    private HeaderNews mHeader;
    private NewsAttributes mAttributes;
    private String mContent;

    public News() {
        this.mAttributes = new NewsAttributes();
        this.mHeader = new HeaderNews();
        this.mContent = "";
    }

    public News(HeaderNews header) {
        this.mAttributes = new NewsAttributes();
        this.mHeader = header;
        this.mContent = "";
    }

    public News(NewsAttributes attributes, HeaderNews header, String content) {
        this.mAttributes = attributes;
        this.mHeader = header;
        this.mContent = content;
    }

    public NewsAttributes getAttributes() {
        return mAttributes;
    }

    public void setAttributes(NewsAttributes attributes) {
        this.mAttributes = attributes;
    }

    public HeaderNews getHeader() {
        return mHeader;
    }

    public void setHeader(HeaderNews header) {
        this.mHeader = header;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public String getLikesAPIUrl() {
        return "https://" + getAttributes().getProject() + ".onliner.by/sdapi/news.api/" + getAttributes().getProject() + "/posts/" + getAttributes().getId() + "/likes";
    }
}
