package by.onliner.news.Structures.User;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Описание пользователя авторизованного
 */
public class User {
    @SerializedName("avatar")
    private final UserAvatar avatar;
    @SerializedName("email")
    private final String mEmail;
    @SerializedName("id")
    private final String mId;
    @SerializedName("phone")
    private final String mPhone;
    @SerializedName("nickname")
    private final String mUsername;

    public User(@NonNull String id, @NonNull String username, @NonNull UserAvatar avatar, @Nullable String phone, @Nullable String email) {
        this.mId = id;
        this.mUsername = username;
        this.avatar = avatar;
        this.mPhone = phone;
        this.mEmail = email;
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    @NonNull
    public String getUsername() {
        return this.mUsername;
    }

    @NonNull
    public String getAvatarUrl() {
        return this.avatar.getUrlLarge();
    }

    public String getPhone() {
        return this.mPhone;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public static User GetUserInfo(String username, String password) {
        return null;
    }
}