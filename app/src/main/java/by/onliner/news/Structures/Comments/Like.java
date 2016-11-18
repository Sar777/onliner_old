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

    public Like() {
        this.mCount = 0;
        this.mIsBest = false;
    }

    public Like(Integer mCount, Boolean best) {
        this.mCount = mCount;
        this.mIsBest = best;
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
