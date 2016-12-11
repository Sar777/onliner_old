package by.onliner.news.structures.news;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Заголовок новости
 */
public class NewsHeader implements Parcelable {
    /**
     * Название
     */
    private String mTitle;
    /**
     * Количество просмотров
     */
    private Integer mView;
    /**
     * Количество комментариев
     */
    private Integer mComments;
    /**
     * Дата публикации(Строковый вариант)
     */
    private String mPostDate;
    /**
     * Дата публикации(Unix формат)
     */
    private Integer mPostDateUnix;
    /**
     * URL адрес изображения для новости
     */
    private String mImage;
    /**
     * URL адрес новости
     */
    private String mUrl;

    /**
     * Дополнительный атрибуты к заголовку
     */
    private HeaderAttributes mAttributes;

    public NewsHeader() {
        this.mTitle = "";
        this.mView = 0;
        this.mComments = 0;
        this.mPostDate = "<unknown>";
        this.mPostDateUnix = 0;
        this.mImage = "";
        this.mUrl = "";
        this.mAttributes = new HeaderAttributes();
    }

    public NewsHeader(String title) {
        this.mTitle = title;
        this.mView = 0;
        this.mComments = 0;
        this.mPostDate = "<unknown>";
        this.mPostDateUnix = 0;
        this.mImage = "";
        this.mUrl = "";
        this.mAttributes = new HeaderAttributes();
    }

    public NewsHeader(String title, Integer views, Integer comments, String postDate, Integer postDateUnix, String image, String url, HeaderAttributes attributes) {
        this.mTitle = title;
        this.mView = views;
        this.mComments = comments;
        this.mPostDate = postDate;
        this.mPostDateUnix = postDateUnix;
        this.mImage = image;
        this.mUrl = url;
        this.mAttributes = attributes;
    }

    public NewsHeader(NewsHeader headerNews) {
        this.mTitle = headerNews.mTitle;
        this.mView = headerNews.getView();
        this.mComments = headerNews.getComments();
        this.mPostDate = headerNews.getPostDate();
        this.mPostDateUnix = headerNews.getPostDateUnix();
        this.mImage = headerNews.getImage();
        this.mUrl = headerNews.getUrl();
        this.mAttributes = headerNews.getAttributes();
    }

    protected NewsHeader(Parcel in) {
        mTitle = in.readString();
        mPostDate = in.readString();
        mImage = in.readString();
        mUrl = in.readString();
    }

    /**
     * Проверка заголовка на валидность
     * @return Истина и ложь
     */
    public Boolean isValid() {
        return !mTitle.isEmpty() && !mImage.isEmpty() && !mUrl.isEmpty();
    }

    /**
     * Получение заголовка новости
     * @return Заголовок новости
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Установка заголовка новости
     * @param title Заголовок новости
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * Получения количества просмотров новости
     * @return Просмотры новости
     */
    public Integer getView() {
        return mView;
    }

    /**
     * Установка количества просмотров новости
     * @param view Просмотры новости
     */
    public void setView(Integer view) {
        this.mView = view;
    }

    /**
     * Получение количества комментариев
     * @return Количество комментариев
     */
    public Integer getComments() {
        return mComments;
    }

    /**
     * Установка количества комментариев
     * @param comments Количество комментариев
     */
    public void setComments(Integer comments) {
        this.mComments = comments;
    }

    /**
     * Получение даты публикации
     * @return Дата публикации в строков формате
     */
    public String getPostDate() {
        return mPostDate;
    }

    /**
     * Установка даты публикации
     * @param postDate Дата публикации в строков формате
     */
    public void setPostDate(String postDate) {
        this.mPostDate = postDate;
    }

    /**
     * Получение даты публикации в unix формате
     * @return Дата публикации в unix формате
     */
    public Integer getPostDateUnix() {
        return mPostDateUnix;
    }

    /**
     * Установка даты публикации в unix формате
     * @param postDateUnix Дата публикации в unix формате
     */
    public void setPostDateUnix(Integer postDateUnix) {
        this.mPostDateUnix = postDateUnix;
    }

    /**
     * Получение URL изображения новости
     * @return URL изображения новости
     */
    public String getImage() {
        return mImage;
    }

    /**
     * Установка URL изображения новости
     * @param image URL изображение новости
     */
    public void setImage(String image) {
        this.mImage = image;
    }

    /**
     * Получение URL адреса новости
     * @return URL адрес новости
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Установка URL адреса новости
     * @param url URL адрес новости
     */
    public void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * Получение дополнительных атрибутов для заголовка новости
     * @return Дополнительне атрибуты для заголовка новости(Количество фото, видео и т.д)
     */
    public HeaderAttributes getAttributes() {
        return mAttributes;
    }

    /**
     * Установка допольнительных атрибутнов для заголовка новости
     * @param attributes Дополнительне атрибуты для заголовка новости(Количество фото, видео и т.д)
     */
    public void setAttributes(HeaderAttributes attributes) {
        this.mAttributes = attributes;
    }

    @Override
    public String toString() {
        return "NewsHeader{" +
                "mTitle='" + mTitle + '\'' +
                ", mView=" + mView +
                ", mComments=" + mComments +
                ", mPostDate='" + mPostDate + '\'' +
                ", mPostDateUnix=" + mPostDateUnix +
                ", mImage='" + mImage + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mAttributes=" + mAttributes +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPostDate);
        dest.writeString(mImage);
        dest.writeString(mUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsHeader> CREATOR = new Creator<NewsHeader>() {
        @Override
        public NewsHeader createFromParcel(Parcel in) {
            return new NewsHeader(in);
        }

        @Override
        public NewsHeader[] newArray(int size) {
            return new NewsHeader[size];
        }
    };
}
