package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Цитаты комментария
 */
public class CommentQuote implements Serializable {
    /**
     * Текст цитаты
     */
    private String mText;
    /**
     * Автор
     */
    private String mAuthor;
    /**
     * Ссылка на вложенную цитату
     */
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

    /**
     * Получение текста цитаты
     * @return Текст цитаты
     */
    public String getText() {
        return mText;
    }

    /**
     * Установки текста цитаты
     * @param text Текст цитаты
     */
    public void setText(String text) {
        this.mText = text;
    }

    /**
     * Получение автора цитаты
     * @return Автор цитаты
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Установка автора цитаты
     * @param author Автор цитаты
     */
    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    /**
     * Получения цитаты
     * @return Цитата
     */
    public CommentQuote getQuote() {
        return mQuote;
    }

    /**
     * Установка цитаты
     * @param quote Цитата
     */
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
