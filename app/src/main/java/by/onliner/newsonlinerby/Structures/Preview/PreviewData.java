package by.onliner.newsonlinerby.Structures.Preview;

public class PreviewData {
    private String Title;
    private String Text;
    private String ImageUrl;
    private Integer Comments;
    private Integer Views;
    private String Date;
    private Integer DateUnix;

    private Boolean IsUpd;

    public PreviewData(String title, String text, String imageUrl, Integer comments, Integer views, String date, Integer dateUnix, Boolean upd) {
        Title = new String(title);
        Text = new String(text);
        ImageUrl = new String(imageUrl);
        Comments = new Integer(comments);
        Views = new Integer(views);
        Date = new String(date);
        DateUnix = new Integer(dateUnix);
        IsUpd = new Boolean(upd);
    }

    public PreviewData(PreviewData data) {
        Title = new String(data.getTitle());
        Text = new String(data.getText());
        ImageUrl = new String(data.getImageUrl());
        Comments = new Integer(data.getComments());
        Views = new Integer(data.getViews());
        Date = new String(data.getDate());
        DateUnix = new Integer(data.getDateUnix());
        IsUpd = new Boolean(data.isUpd());
    }

    public PreviewData() {
        Title = "";
        Text = "";
        ImageUrl = "";
        Comments = 0;
        Views = 0;
        Date = "";
        DateUnix = 0;
        IsUpd = false;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Integer getComments() {
        return Comments;
    }

    public void setComments(Integer comments) {
        Comments = comments;
    }

    public Integer getViews() {
        return Views;
    }

    public void setViews(Integer views) {
        Views = views;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getDateUnix() {
        return DateUnix;
    }

    public void setDateUnix(Integer dateUnix) {
        DateUnix = dateUnix;
    }

    public Boolean isUpd() {
        return IsUpd;
    }

    public void setUpd(Boolean upd) {
        IsUpd = upd;
    }

    public Boolean isValid() {
        if (Title == null || ImageUrl == null)
            return false;

        return true;
    }
}
