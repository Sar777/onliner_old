package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Created by orion on 29.10.16.
 */

public class CommentQuote implements Serializable {
    private String mText;
    private String mAuthor;
    private CommentQuote mQuote;

    public CommentQuote() {
        this.mText = "Unknown";
        this.mAuthor = "Unknown";
    }

    public CommentQuote(String text, String author, CommentQuote quote) {
        this.mText = text;
        this.mAuthor = author;
        this.mQuote = quote;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public CommentQuote getQuote() {
        return mQuote;
    }

    public void setQuote(CommentQuote quote) {
        this.mQuote = quote;
    }

    @Override
    public String toString() {
        return "CommentQuote{" +
                "mText='" + mText + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mQuote=" + mQuote +
                '}';
    }
}
