package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Информация о лайках комментария
 */
public class Like implements Serializable {
    /**
     * Уникальный айди комментария
     */
    private Integer mCommentId;
    /**
     * Количество лайков
     */
    private Integer mCount;
    /**
     * Является ли комментария лучшим(топ)
     */
    private Boolean mIsBest;

    public Like() {
        this.mCommentId = 0;
        this.mCount = 0;
        this.mIsBest = false;
    }

    public Like(Integer commentId, Integer mCount, Boolean best) {
        this.mCommentId = commentId;
        this.mCount = mCount;
        this.mIsBest = best;
    }

    /**
     * Получение айди комментария
     * @return Айди комментария
     */
    public Integer getCommentId() {
        return mCommentId;
    }

    /**
     * Установка айди комментария
     * @param commentId Айди комментрия
     */
    public void setCommentId(Integer commentId) {
        this.mCommentId = commentId;
    }

    /**
     * Получение количество лайков
     * @return Количество лайков
     */
    public Integer getCount() {
        return mCount;
    }

    /**
     * Установка количества лайков
     * @param count Количетсво лайков
     */
    public void setCount(Integer count) {
        this.mCount = count;
    }

    /**
     * Является ли комментарий лучшим
     * @return Лучший ли комментария
     */
    public Boolean isBest() {
        return mIsBest;
    }

    /**
     * Установка флаг для лучшего комментария
     * @param best Лучший ли комментарий
     */
    public void setBest(Boolean best) {
        this.mIsBest = best;
    }
}
