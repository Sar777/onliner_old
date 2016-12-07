package by.onliner.news.structures.news;

import java.io.Serializable;

/**
 * Атрибуты заголовка новости
 */
public class HeaderAttributes implements Serializable {
    /**
     * Была ли новость обновлена на сайте(Маркер)
     */
    private Boolean mUpd;
    /**
     * Присутствует ли видео у новости
     */
    private Boolean mCamera;
    /**
     * Количество фотографий у новости
     */
    private Integer mPhotos;

    public HeaderAttributes() {
        this.mUpd = false;
        this.mCamera = false;
        this.mPhotos = 0;
    }

    public HeaderAttributes(Boolean upd, Boolean camera, Integer photos) {
        this.mUpd = upd;
        this.mCamera = camera;
        this.mPhotos = photos;
    }

    public HeaderAttributes(HeaderAttributes attr) {
        this.mUpd = attr.getUpd();
        this.mCamera = attr.getCamera();
        this.mPhotos = attr.getPhotos();
    }

    /**
     * Проверка новости на обновленность
     * @return Новость обновлена или нет
     */
    public Boolean getUpd() {
        return mUpd;
    }

    /**
     * Установка статуса обновленность новости
     * @param upd Обновлена или нет
     */
    public void setUpd(Boolean upd) {
        this.mUpd = upd;
    }

    /**
     * Получение информации о присутствии у новости видео
     * @return Есть ли видео у новости
     */
    public Boolean getCamera() {
        return mCamera;
    }

    /**
     * Установка видео у новости
     * @param camera Есть ли видео у новости
     */
    public void setCamera(Boolean camera) {
        this.mCamera = camera;
    }

    /**
     * Получение количества фотографий у новости
     * @return Количество фотографий у новости
     */
    public Integer getPhotos() {
        return mPhotos;
    }

    /**
     * Установка количества фотографий к новости
     * @param photos Количество фотографий у новости
     */
    public void setPhotos(Integer photos) {
        this.mPhotos = photos;
    }
}
