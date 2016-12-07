package by.onliner.news.structures.news;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Содержимое новости
 */
public class News implements Parcelable {
    /**
     * Содержанеие заголовка новости где хранится информация о видео, изображении и так далее
     */
    @NonNull
    private NewsPreview mPreview;
    /**
     * Атрибуты новости
     */
    @NonNull
    private NewsAttributes mAttributes;
    /**
     * Содержимое новости в html
     */
    @NonNull
    private String mContent;

    public News(@NonNull String url, @NonNull String project, String title, @NonNull String content) {
        this.mAttributes = new NewsAttributes(url, project);
        this.mPreview = new NewsPreview(title);
        this.mContent = content;
    }

    public News(NewsPreview preview) {
        this.mAttributes = new NewsAttributes();
        this.mPreview = preview;
        this.mContent = "";
    }

    public News(@NonNull NewsAttributes attributes, @NonNull NewsPreview preview, @NonNull String content) {
        this.mAttributes = attributes;
        this.mPreview = preview;
        this.mContent = content;
    }

    protected News(Parcel in) {
        mPreview = in.readParcelable(NewsPreview.class.getClassLoader());
        mAttributes = in.readParcelable(NewsAttributes.class.getClassLoader());
        mContent = in.readString();
    }

    /**
     * Получение ссылки на атрибуты новости
     * @return Атрибут новости
     */
    public NewsAttributes getAttributes() {
        return mAttributes;
    }

    /**
     * Установка атрибута новости
     * @param attributes Атрибут новости
     */
    public void setAttributes(NewsAttributes attributes) {
        this.mAttributes = attributes;
    }

    /**
     * Получение заголовка новости
     * @return Заголовок новости
     */
    public NewsPreview getPreview() {
        return mPreview;
    }

    /**
     * Установка заголовка новости
     * @param preview Заголовк новости
     */
    public void setPreview(NewsPreview preview) {
        this.mPreview = preview;
    }

    /**
     * Установка содержимого новости в html
     * @param content Содержимое новости в html
     */
    public void setNewsContent(String content) {
        this.mContent = content;
    }

    /**
     * Получение содержимого новости в html
     * @return Содержимое новости в html
     */
    public String getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return "News{" +
                "mPreview=" + mPreview +
                ", mAttributes=" + mAttributes +
                ", mContent='" + mContent.toString() + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mPreview, flags);
        dest.writeParcelable(mAttributes, flags);
        dest.writeString(mContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
