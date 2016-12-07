package by.onliner.news.structures.comments;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Информация о комментарии
 */
public class Comment implements Parcelable {
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

    protected Comment(Parcel in) {
        Id = in.readInt();
        mAuthor = in.readParcelable(Author.class.getClassLoader());
        mDate = in.readString();
        mLikes = in.readParcelable(Like.class.getClassLoader());
        mText = in.readString();
        mAvatarURL = in.readString();
        mQuote = in.readParcelable(CommentQuote.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeParcelable(mAuthor, i);
        parcel.writeString(mDate);
        parcel.writeParcelable(mLikes, i);
        parcel.writeString(mText);
        parcel.writeString(mAvatarURL);
        parcel.writeParcelable(mQuote, i);
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
