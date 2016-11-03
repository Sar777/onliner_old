package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Информация о комментарии
 */
public class Comment implements Serializable {
    /**
     * Уникальный айди комментария
     */
    private Integer Id;
    /**
     * Автор комментария
     */
    private Author mAuthor;
    /**
     * Дата комментария
     */
    private String mDate;
    /**
     * Информация о полученных лайках
     */
    private Like mLikes;
    /**
     * Текст комментария
     */
    private String mText;
    /**
     * Адрес аватарки владельца комментария
     */
    private String mAvatarURL;
    /**
     * Цитаты комментария
     */
    private CommentQuote mQuote;

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
        this.mQuote = new CommentQuote();
    }

    /**
     * Получения айди комментария
     * @return Айди комментария
     */
    public Integer getId() {
        return Id;
    }

    /**
     * Установка айди комментария
     * @param id Айди комментария
     */
    public void setId(Integer id) {
        Id = id;
    }

    /**
     * Получения текста комментария
     * @return Текст комментария
     */
    public String getText() {
        return mText;
    }

    /**
     * Установка текста комментария
     * @param text Текст комментария
     */
    public void setText(String text) {
        mText = text;
    }

    /**
     * Получения даты комментария
     * @return Дата комментария
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Установка даты комментария
     * @param date Дата комментария
     */
    public void setDate(String date) {
        this.mDate = date;
    }

    /**
     * Получение информации о лайках комментария
     * @return Лайки комментария
     */
    public Like getLikes() {
        return mLikes;
    }

    /**
     * Установка лайков для комментария
     * @param likes Лайк комментария
     */
    public void setLikes(Like likes) {
        this.mLikes = likes;
    }

    /**
     * Получения автора комментария
     * @return Автор комментария
     */
    public Author getAuthor() {
        return mAuthor;
    }

    /**
     * Установка автора комментария
     * @param author Автор комментария
     */
    public void setAuthor(Author author) {
        this.mAuthor = author;
    }

    /**
     * Получения адреса url автора комментария
     * @return URL аватарки владельца комментария
     */
    public String getAvatarURL() {
        return mAvatarURL;
    }

    /**
     * Установки url аватарки автора комментария
     * @param avatarURL URL аватарки автора комментария
     */
    public void setAvatarURL(String avatarURL) {
        this.mAvatarURL = avatarURL;
    }

    /**
     * Получение цитаты комментария
     * @return Цитата комментария(если имеется)
     */
    public CommentQuote getQuote() {
        return mQuote;
    }

    /**
     * Установка цитаты для комментария
     * @param quote Цитата комментария
     */
    public void setQuote(CommentQuote quote) {
        this.mQuote = quote;
    }
}
