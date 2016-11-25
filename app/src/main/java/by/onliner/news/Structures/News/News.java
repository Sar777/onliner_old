package by.onliner.news.Structures.News;


import android.support.annotation.NonNull;

import org.jsoup.nodes.Document;

import java.io.Serializable;

/**
 * Содержимое новости
 */
public class News implements Serializable {
    /**
     * Содержанеие заголовка новости где хранится информация о видео, изображении и так далее
     */
    @NonNull
    private NewsHeader mHeader;
    /**
     * Атрибуты новости
     */
    @NonNull
    private NewsAttributes mAttributes;
    /**
     * Содержимое новости в html
     */
    @NonNull
    private transient Document mContent;

    public News(Document content) {
        this.mAttributes = new NewsAttributes();
        this.mHeader = new NewsHeader();
        this.mContent = content;
    }

    public News(NewsHeader header) {
        this.mAttributes = new NewsAttributes();
        this.mHeader = header;
        this.mContent = null;
    }

    public News(NewsAttributes attributes, NewsHeader header, Document content) {
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
     * @param doc Содержимое новости
     */
    public void setContent(Document doc) {
        this.mContent = doc;
    }

    /**
     * Получение содержимого новости в html
     * @return Содержимое новости
     */
    public Document getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return "News{" +
                "mHeader=" + mHeader +
                ", mAttributes=" + mAttributes +
                ", mContent='" + mContent.toString() + '\'' +
                '}';
    }
}
