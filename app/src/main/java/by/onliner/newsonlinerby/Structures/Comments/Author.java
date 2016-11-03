package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Информации о пользователе
 */
public class Author implements Serializable {
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
}
