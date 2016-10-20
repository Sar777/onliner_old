package by.onliner.newsonlinerby.Structures;

/**
 * Created by Mi Air on 20.10.2016.
 */

public class Comment {
    private String mAuthor;
    private String mDate;
    private Integer mLikes;
    private String mText;
    private String mAvatarURL;

    public Comment(String author, String date, int likes, String text) {
        this.mAuthor = author;
        this.mDate = date;
        this.mLikes = likes;
        this.mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public Integer getLikes() {
        return mLikes;
    }

    public void setLikes(int likes) {
        this.mLikes = likes;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getAvatarURL() {
        return mAvatarURL;
    }

    public void setAvatarURL(String mAvatarURL) {
        this.mAvatarURL = mAvatarURL;
    }
}
