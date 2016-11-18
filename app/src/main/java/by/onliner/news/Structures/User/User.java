package by.onliner.news.Structures.User;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import by.onliner.news.Services.ServiceFactory;
import by.onliner.news.Services.User.UserService;
import by.onliner.news.Structures.Credentials.Credentials;
import retrofit2.Response;

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

    public static User createUser(String username, String password) {
        String accessToken = Credentials.Network.getAccessToken(username, password);
        String userId = Network.getUserIdByToken(accessToken);
        if (userId == null)
            return null;

        User user = null;
        try {
            Response<User> response = ServiceFactory.createRetrofitService(UserService.class, UserService.USER_API).getUser(ServiceFactory.getAccessToken(accessToken), userId).execute();
            if (!response.isSuccessful())
                return null;

            user = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static class Network {
        public static String getUserIdByToken(String token) {
            try {
                Response<User> response = ServiceFactory.createRetrofitService(UserService.class, UserService.USER_API).getUser(ServiceFactory.getAccessToken(token)).execute();
                if (!response.isSuccessful())
                    return null;

                return response.body().getId();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}