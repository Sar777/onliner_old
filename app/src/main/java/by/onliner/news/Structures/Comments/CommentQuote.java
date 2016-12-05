package by.onliner.news.Structures.Comments;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Цитаты комментария
 */
public class CommentQuote implements Parcelable {
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

    protected CommentQuote(Parcel in) {
        mText = in.readString();
        mAuthor = in.readString();
        mQuote = in.readParcelable(CommentQuote.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mText);
        parcel.writeString(mAuthor);
        parcel.writeParcelable(mQuote, i);
    }

    public static final Creator<CommentQuote> CREATOR = new Creator<CommentQuote>() {
        @Override
        public CommentQuote createFromParcel(Parcel in) {
            return new CommentQuote(in);
        }

        @Override
        public CommentQuote[] newArray(int size) {
            return new CommentQuote[size];
        }
    };
}
