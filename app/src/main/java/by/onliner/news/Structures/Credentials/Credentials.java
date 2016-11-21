package by.onliner.news.Structures.Credentials;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import by.onliner.news.App;
import by.onliner.news.Constants.Constant;
import by.onliner.news.Listeners.Credentials.OnCredentialsRefreshListener;
import by.onliner.news.Services.Credential.CredentialService;
import by.onliner.news.Services.ServiceFactory;
import retrofit2.Response;

/**
 * Токены авторизации
 */
public class Credentials {
    @SerializedName("access_token")
    private final String mAccessToken;
    @SerializedName("refresh_token")
    private final String mRefreshToken;

    private long mDateStore;

    public Credentials(@NonNull String accessToken, @NonNull String refreshToken) {
        this.mAccessToken = accessToken;
        this.mRefreshToken = refreshToken;

        this.mDateStore = System.currentTimeMillis() / 1000L;
    }

    public void delete() {
        SQLiteDatabase db = App.getDBHelper().getWritableDatabase();
        db.delete(Constant.mTableNameCredentials, null, null);
    }

    public void saveToDB() {
        SQLiteDatabase db = App.getDBHelper().getWritableDatabase();
        db.delete(Constant.mTableNameCredentials, null, null);

        ContentValues cv = new ContentValues();
        cv.put("json", new Gson().toJson(this));
        db.insert(Constant.mTableNameCredentials, null, cv);
    }

    @NonNull
    public String getAccessToken() {
        return String.format("%s %s", Constant.mOnlinerMagic, this.mAccessToken);
    }

    @NonNull
    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    @NonNull
    public long getDateStore() {
        return mDateStore;
    }

    public static void getCredintials(final String username, final String password, final OnCredentialsRefreshListener listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final CredentialService service = ServiceFactory.createRetrofitService(CredentialService.class, CredentialService.CREDENTIALS_API);
                try {
                    Response<Credentials> response = service.getPasswordCredentials(username, password, CredentialService.CLIENT_ID, CredentialService.GRANT_TYPE).execute();
                    listener.onRefresh(response.body());
                } catch (IOException e) {
                    listener.onRefresh(null);
                }
            }
        });
    }

    public static Credentials create() {
        SQLiteDatabase db = App.getDBHelper().getReadableDatabase();
        Cursor cursor = db.query(Constant.mTableNameCredentials, null, null, null, null, null, null);

        Credentials credentials = null;
        if (cursor.moveToFirst())
            credentials = new Gson().fromJson(cursor.getString(0), Credentials.class);

        cursor.close();
        return credentials;
    }
}