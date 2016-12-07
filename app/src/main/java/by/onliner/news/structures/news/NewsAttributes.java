package by.onliner.news.structures.news;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Дополнительные атрибуты для новости
 */
public class NewsAttributes implements Parcelable {
    /**
     * Айди новости
     */
    private Integer mId;
    /**
     * Принадлежность новости к разделу(auto, people и так т.д)
     */
    private String mProject;
    /**
     * URL aдрес новости
     */
    private String mUrl;

    public NewsAttributes() {
        this.mId = 0;
        this.mProject = "";
        this.mUrl = "";
    }

    public NewsAttributes(String url, String project) {
        this.mId = 0;
        this.mUrl = url;
        this.mProject = project;
    }

    public NewsAttributes(Integer id, String project, String url) {
        this.mId = id;
        this.mProject = project;
        this.mUrl = url;
    }

    protected NewsAttributes(Parcel in) {
        mId = in.readInt();
        mProject = in.readString();
        mUrl = in.readString();
    }

    /**
     * Получение айди новости
     * @return Айди новости
     */
    public Integer getId() {
        return mId;
    }

    /**
     * Установка айди новости
     * @param id Айди новости
     */
    public void setId(Integer id) {
        mId = id;
    }

    /**
     * Получение имени раздела к которому принадлежит новость
     * @return Имя раздела
     */
    public String getProject() {
        return mProject;
    }

    /**
     * Установка имени раздела новости
     * @param project Раздел новости
     */
    public void setProject(String project) {
        this.mProject = project;
    }

    /**
     * Получение URL адрес новости
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

    @Override
    public String toString() {
        return "NewsAttributes{" +
                "mId=" + mId +
                ", mProject='" + mProject + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mProject);
        dest.writeString(mUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsAttributes> CREATOR = new Creator<NewsAttributes>() {
        @Override
        public NewsAttributes createFromParcel(Parcel in) {
            return new NewsAttributes(in);
        }

        @Override
        public NewsAttributes[] newArray(int size) {
            return new NewsAttributes[size];
        }
    };
}
