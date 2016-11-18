package by.onliner.news.Structures.User;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Аватарка пользователя
 */
public class UserAvatar {
    @SerializedName("large")
    private final String mUrlLarge;
    @SerializedName("small")
    private final String mUrlSmall;

    public UserAvatar(@NonNull String urlLarge, @NonNull String urlSmall) {
        this.mUrlLarge = urlLarge;
        this.mUrlSmall = urlSmall;
    }

    /**
     * Получение url адреса аватарки пользователя
     * @return Url адреса аватарки пользователя
     */
    @NonNull
    public String getUrlLarge() {
        return this.mUrlLarge;
    }

    /**
     * Получение url адреса аватарки пользователя
     * @return Url адреса аватарки пользователя
     */
    @NonNull
    public String getUrlSmall() {
        return this.mUrlSmall;
    }
}
