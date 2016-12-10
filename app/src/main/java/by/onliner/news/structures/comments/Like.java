package by.onliner.news.structures.comments;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Информация о лайках комментария
 */
public class Like implements Parcelable {
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

    /**
     * Комментарий был лайкнут
     */
    @SerializedName("like")
    private Boolean mIsLike;

    public Like() {
        this.mCount = 0;
        this.mIsBest = false;
        this.mIsLike = false;
    }

    public Like(Parcel in) {
        mCount = in.readInt();
        mIsBest = (Boolean) in.readValue(Boolean.class.getClassLoader());
        mIsLike = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public Like(Integer count, Boolean best, Boolean like) {
        this.mCount = count != null ? count : 0;
        this.mIsBest = best != null ? best : false;
        this.mIsLike = like != null ? like : false;
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

    @Override
    public String toString() {
        return "Like{" +
                "mCount=" + mCount +
                ", mIsBest=" + mIsBest +
                ", mIsLike=" + mIsLike +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mCount);
        parcel.writeValue(mIsBest);
        parcel.writeValue(mIsLike);
    }

    public static final Creator<Like> CREATOR = new Creator<Like>() {
        @Override
        public Like createFromParcel(Parcel in) {
            return new Like(in);
        }

        @Override
        public Like[] newArray(int size) {
            return new Like[size];
        }
    };
}
