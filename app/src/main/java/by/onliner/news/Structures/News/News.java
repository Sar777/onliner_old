package by.onliner.news.Structures.News;


import java.io.Serializable;

/**
 * Содержимое новости
 */
public class News implements Serializable {
    /**
     * Содержанеие заголовка новости где хранится информация о видео, изображении и так далее
     */
    private NewsHeader mHeader;
    /**
     * Атрибуты новости
     */
    private NewsAttributes mAttributes;
    /**
     * Содержимое новости в html
     */
    private String mContent;

    public News(String content) {
        this.mAttributes = new NewsAttributes();
        this.mHeader = new NewsHeader();
        this.mContent = content;
    }

    public News(NewsHeader header) {
        this.mAttributes = new NewsAttributes();
        this.mHeader = header;
        this.mContent = "";
    }

    public News(NewsAttributes attributes, NewsHeader header, String content) {
        this.mAttributes = attributes;
        this.mHeader = header;
        this.mContent = content;
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
    public NewsHeader getHeader() {
        return mHeader;
    }

    /**
     * Установка заголовка новости
     * @param header Заголовк новости
     */
    public void setHeader(NewsHeader header) {
        this.mHeader = header;
    }

    /**
     * Установка содержимого новости в html
     * @param content Содержимое новости в html
     */
    public void setContent(String content) {
        this.mContent = content;
    }

    /**
     * Получение содержимого новости в html
     * @return Содержимое новости в html
     */
    public String getContent() {
        return mContent;
    }
}
