package by.onliner.news.Structures.Comments;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Информация о лайках комментария
 */
public class Like implements Serializable {
    /**
     * Количество лайков
     */
    @SerializedName("counter")
    private Integer mCount;
    /**
     * Является ли комментария лучшим(топ)
     */
    @SerializedName("best")
    private Boolean mIsBest;

    @SerializedName("like")
    private Boolean mIsLike;

    public Like() {
        this.mCount = 0;
        this.mIsBest = false;
    }

    public Like(Integer count, Boolean best, Boolean like) {
        this.mCount = count;
        this.mIsBest = best;
        this.mIsLike = like;
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
        this.mCount = Integer.valueOf(count);
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

    /**
     * Является ли комментр лайкнутым
     * @return Лайкнутый коммент
     */
    public Boolean getIsLike() {
        return mIsLike;
    }

    /**
     * Установка лайка для комментария
     * @param like Лайк комментарий
     */
    public void setIsLike(Boolean like) {
        this.mIsLike = like;
    }
}
