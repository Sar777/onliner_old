package by.onliner.news.structures.comments;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Информации о пользователе
 */
public class Author implements Parcelable {
    /**
     * Уникальный айд
     */
    private Integer mId;
    /**
     * Имя
     */
    private String mName;

    public Author() {
    }

    public Author(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
    }

    public Author(String name, Integer id) {
        this.mName = name;
        this.mId = id;
    }

    /**
     * Получение айди пользователя
     * @return Айди пользователя
     */
    public Integer getId() {
        return mId;
    }

    /**
     * Установка айди пользователя
     * @param id Айди пользователя
     */
    public void setId(Integer id) {
        this.mId = id;
    }

    /**
     * Получение имени пользователя
     * @return Имя пользователя
     */
    public String getName() {
        return mName;
    }

    /**
     * Установка имени пользователя
     * @param name Имя пользователя
     */
    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
