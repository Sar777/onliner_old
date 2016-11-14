package by.onliner.news.Structures.News;

/**
 * Заголовок новости
 */
public class HeaderNews {
    /**
     * Название
     */
    private String title;
    /**
     * Количество просмотров
     */
    private Integer views;
    /**
     * Количество комментариев
     */
    private Integer comments;
    /**
     * Дата публикации(Строковый вариант)
     */
    private String postDate;
    /**
     * Дата публикации(Unix формат)
     */
    private Integer postDateUnix;
    /**
     * URL адрес изображения для новости
     */
    private String image;
    /**
     * URL адрес новости
     */
    private String url;

    /**
     * Дополнительный атрибуты к заголовку
     */
    private HeaderAttributes attributes;

    public HeaderNews() {
        this.title = "";
        this.views = 0;
        this.comments = 0;
        this.postDate = "<unknown>";
        this.postDateUnix = 0;
        this.image = "";
        this.url = "";
        this.attributes = new HeaderAttributes();
    }

    public HeaderNews(String title, Integer views, Integer comments, String postDate, Integer postDateUnix, String image, String url, HeaderAttributes attributes) {
        this.title = title;
        this.views = views;
        this.comments = comments;
        this.postDate = postDate;
        this.postDateUnix = postDateUnix;
        this.image = image;
        this.url = url;
        this.attributes = attributes;
    }

    public HeaderNews(HeaderNews headerNews) {
        this.title = headerNews.title;
        this.views = headerNews.getViews();
        this.comments = headerNews.getComments();
        this.postDate = headerNews.getPostDate();
        this.postDateUnix = headerNews.getPostDateUnix();
        this.image = headerNews.getImage();
        this.url = headerNews.getUrl();
        this.attributes = headerNews.getAttributes();
    }

    /**
     * Проверка заголовка на валидность
     * @return Истина и ложь
     */
    public Boolean isValid() {
        return !title.isEmpty() && !image.isEmpty();
    }

    /**
     * Получение заголовка новости
     * @return Заголовок новости
     */
    public String getTitle() {
        return title;
    }

    /**
     * Установка заголовка новости
     * @param title Заголовок новости
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получения количества просмотров новости
     * @return Просмотры новости
     */
    public Integer getViews() {
        return views;
    }

    /**
     * Установка количества просмотров новости
     * @param views Просмотры новости
     */
    public void setViews(Integer views) {
        this.views = views;
    }

    /**
     * Получение количества комментариев
     * @return Количество комментариев
     */
    public Integer getComments() {
        return comments;
    }

    /**
     * Установка количества комментариев
     * @param comments Количество комментариев
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    /**
     * Получение даты публикации
     * @return Дата публикации в строков формате
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * Установка даты публикации
     * @param postDate Дата публикации в строков формате
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    /**
     * Получение даты публикации в unix формате
     * @return Дата публикации в unix формате
     */
    public Integer getPostDateUnix() {
        return postDateUnix;
    }

    /**
     * Установка даты публикации в unix формате
     * @param postDateUnix Дата публикации в unix формате
     */
    public void setPostDateUnix(Integer postDateUnix) {
        this.postDateUnix = postDateUnix;
    }

    /**
     * Получение URL изображения новости
     * @return URL изображения новости
     */
    public String getImage() {
        return image;
    }

    /**
     * Установка URL изображения новости
     * @param image URL изображение новости
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Получение URL адреса новости
     * @return URL адрес новости
     */
    public String getUrl() {
        return url;
    }

    /**
     * Установка URL адреса новости
     * @param url URL адрес новости
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Получение дополнительных атрибутов для заголовка новости
     * @return Дополнительне атрибуты для заголовка новости(Количество фото, видео и т.д)
     */
    public HeaderAttributes getAttributes() {
        return attributes;
    }

    /**
     * Установка допольнительных атрибутнов для заголовка новости
     * @param attributes Дополнительне атрибуты для заголовка новости(Количество фото, видео и т.д)
     */
    public void setAttributes(HeaderAttributes attributes) {
        this.attributes = attributes;
    }
}
