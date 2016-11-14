package by.onliner.news.Structures.News;


/**
 * Содержимое новости
 */
public class News {
    /**
     * Содержанеие заголовка новости где хранится информация о видео, изображении и так далее
     */
    private HeaderNews mHeader;
    /**
     * Атрибуты новости
     */
    private NewsAttributes mAttributes;
    /**
     * Содержимое новости в html
     */
    private String mContent;

    public News() {
        this.mAttributes = new NewsAttributes();
        this.mHeader = new HeaderNews();
        this.mContent = "";
    }

    public News(HeaderNews header) {
        this.mAttributes = new NewsAttributes();
        this.mHeader = header;
        this.mContent = "";
    }

    public News(NewsAttributes attributes, HeaderNews header, String content) {
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
    public HeaderNews getHeader() {
        return mHeader;
    }

    /**
     * Установка заголовка новости
     * @param header Заголовк новости
     */
    public void setHeader(HeaderNews header) {
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

    /**
     * Формирование ссылки для запроса на получение лайков для комментариев
     * @return Ссылка для получения лайков
     */
    public String getLikesAPIUrl() {
        return "https://" + getAttributes().getProject() + ".onliner.by/sdapi/news.api/" + getAttributes().getProject() + "/posts/" + getAttributes().getId() + "/likes";
    }
}
