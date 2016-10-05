package by.onliner.newsonlinerby.Structures;

/**
 * Created by Mi Air on 06.10.2016.
 */

public class HeaderNews {
    private String title;
    private Integer views;
    private Integer comments;
    private String postDate;
    private Integer postDateUnix;
    private String image;

    private HeaderAttributes attributes;

    public HeaderNews() {
        this.title = "";
        this.views = 0;
        this.comments = 0;
        this.postDate = "<unknown>";
        this.postDateUnix = 0;
        this.image = "";
        this.attributes = new HeaderAttributes();
    }

    public HeaderNews(String title, Integer views, Integer comments, String postDate, Integer postDateUnix, String image, HeaderAttributes attributes) {
        this.title = title;
        this.views = views;
        this.comments = comments;
        this.postDate = postDate;
        this.postDateUnix = postDateUnix;
        this.image = image;
        this.attributes = attributes;
    }

    public HeaderNews(HeaderNews headerNews) {
        this.title = headerNews.title;
        this.views = headerNews.getViews();
        this.comments = headerNews.getComments();
        this.postDate = headerNews.getPostDate();
        this.postDateUnix = headerNews.getPostDateUnix();
        this.image = headerNews.getImage();
        this.attributes = headerNews.getAttributes();
    }

    public Boolean isValid() {
        return !title.isEmpty() && !image.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Integer getPostDateUnix() {
        return postDateUnix;
    }

    public void setPostDateUnix(Integer postDateUnix) {
        this.postDateUnix = postDateUnix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public HeaderAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(HeaderAttributes attributes) {
        this.attributes = attributes;
    }
}
