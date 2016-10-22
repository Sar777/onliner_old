package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Created by Mi Air on 20.10.2016.
 */

public class Comment implements Serializable {
    private Integer Id;
    private Author mAuthor;
    private String mDate;
    private Like mLikes;
    private String mText;
    private String mAvatarURL;

    public Comment(Integer id, Author author, String date, Like likes, String text) {
        this.Id = id;
        this.mAuthor = author;
        this.mDate = date;
        this.mLikes = likes;
        this.mText = text;
    }

    public Comment() {
        this.Id = 0;
        this.mAuthor = new Author();
        this.mLikes = new Like();
        this.mDate = "<Unknown>";
        this.mText = "<Unknown>";
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public void setDate(String date) {
        this.mDate = date;
    }

    public Like getLikes() {
        return mLikes;
    }

    public void setLikes(Like likes) {
        this.mLikes = likes;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        this.mAuthor = author;
    }

    public String getAvatarURL() {
        return mAvatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.mAvatarURL = avatarURL;
    }
}
