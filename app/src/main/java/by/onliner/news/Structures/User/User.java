package by.onliner.news.Structures.User;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import by.onliner.news.App;
import by.onliner.news.Constants.Constant;
import by.onliner.news.Listeners.User.OnUserUpdateListener;
import by.onliner.news.Services.ServiceFactory;
import by.onliner.news.Services.User.UserService;

/**
 * Описание пользователя авторизованного
 */
public class User {
    @SerializedName("avatar")
    private final UserAvatar mAvatar;
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
        this.mAvatar = avatar;
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
    public UserAvatar getAvatar() {
        return this.mAvatar;
    }

    public String getPhone() {
        return this.mPhone;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public void delete() {
        SQLiteDatabase db = App.getDBHelper().getWritableDatabase();
        db.delete(Constant.mTableNameUser, null, null);
    }

    public void saveToDB() {
        SQLiteDatabase db = App.getDBHelper().getWritableDatabase();
        db.delete(Constant.mTableNameUser, null, null);

        ContentValues cv = new ContentValues();
        cv.put("json", new Gson().toJson(this));
        db.insert(Constant.mTableNameUser, null, cv);
    }

    public static User create() {
        SQLiteDatabase db = App.getDBHelper().getReadableDatabase();
        Cursor cursor = db.query(Constant.mTableNameUser, null, null, null, null, null, null);

        User user = null;
        if (cursor.moveToFirst())
            user = new Gson().fromJson(cursor.getString(0), User.class);

        cursor.close();
        return user;
    }

    public static void getUser(final String token, final OnUserUpdateListener listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final UserService service = ServiceFactory.createRetrofitService(UserService.class, UserService.USER_API);
                try {
                    User tempUser = service.getUser(token).execute().body();
                    listener.onUpdate(service.getUser(token, tempUser.getId()).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}