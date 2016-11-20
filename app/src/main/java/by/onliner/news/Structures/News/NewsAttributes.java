package by.onliner.news.Structures.News;

import java.io.Serializable;

/**
 * Дополнительные атрибуты для новости
 */
public class NewsAttributes implements Serializable {
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

    public NewsAttributes(Integer id, String project, String url) {
        this.mId = id;
        this.mProject = project;
        this.mUrl = url;
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
}
